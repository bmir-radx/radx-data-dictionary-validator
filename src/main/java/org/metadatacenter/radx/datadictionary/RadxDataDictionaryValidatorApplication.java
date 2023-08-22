package org.metadatacenter.radx.datadictionary;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class RadxDataDictionaryValidatorApplication implements ApplicationRunner {

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(RadxDataDictionaryValidatorApplication.class, args);

    }

    @Bean
    DataDictionarySpec dataDictionarySpec(ObjectMapper mapper) {
        try {
            var csvMapper = new CsvMapper();
            var inputStream = RadxDataDictionaryValidatorApplication.class.getResourceAsStream("/columns.csv");
            MappingIterator<DataDictionaryColumnDescriptor> iterator = csvMapper.readerWithSchemaFor(DataDictionaryColumnDescriptor.class)
                                                                            .readValues(inputStream);
            var rows = iterator.readAll();
            return new DataDictionarySpec(rows);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Bean
    DatatypeManager datatypeManager(DatatypeManagerFactory datatypeManagerFactory) {
        return datatypeManagerFactory.get();
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        var optionValues = args.getOptionValues("in");
        if(optionValues.isEmpty()) {
            return;
        }

        var out = Files.newOutputStream(Path.of("/tmp/report.tsv"));

        var pth = Path.of(optionValues.get(0));
        if(Files.isDirectory(pth)) {
            Files.walkFileTree(pth, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if(!file.getFileName().toString().startsWith("project")) {
                        return FileVisitResult.CONTINUE;
                    }
                    if(!Files.isHidden(file) && file.getFileName().toString().contains("_DICT_")) {
                        processFile(file, out);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        else {
            processFile(pth, out);
        }

    }

    private void processFile(Path pth, OutputStream out) throws IOException {
        var csv = parseCsv(pth);
        var validator = context.getBean(Validator.class);
        var report = validator.validateDataDictionary(csv);

        var resultWriter = new CSVPrinter(new PrintStream(out), CSVFormat.TDF);

        report.results()
                .stream()

                .filter(r -> r.validationLevel().equals(ValidationLevel.ERROR))              .map(r ->
                         {
                             var fileName = pth.getFileName().toString();
                             var phs = pth.getParent().getFileName().toString();
                             var rowIndex = r.csvRow().rowIndex();
                             var rowNumber = rowIndex + 1;
                             var validationLevel = r.validationLevel().name();
                             var message = r.message();
                             return List.of(phs, fileName, rowNumber, validationLevel, message);
                         })
                .forEach(r -> {
                    try {
                        resultWriter.printRecord(r);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
    }

    private static Csv parseCsv(Path pth) throws IOException {
        try (var inputStream = Files.newInputStream(pth)) {
            return new CsvParser().parseCsv(inputStream);
        }
    }
}
