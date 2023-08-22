package org.metadatacenter.radx.datadictionary;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.metadatacenter.radx.datadictionary.cli.ValidateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import picocli.CommandLine;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
public class RadxDataDictionaryValidatorApplication implements CommandLineRunner, ExitCodeGenerator {

    private final CommandLine.IFactory iFactory;

    @Autowired
    private ApplicationContext applicationContext;

    private int exitCode;

    public RadxDataDictionaryValidatorApplication(CommandLine.IFactory iFactory) {
        this.iFactory = iFactory;
    }

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
    public void run(String... args) {
        var validateCommand = applicationContext.getBean(ValidateCommand.class);
        exitCode = new CommandLine(validateCommand, iFactory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
