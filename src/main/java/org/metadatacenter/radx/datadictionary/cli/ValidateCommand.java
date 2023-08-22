package org.metadatacenter.radx.datadictionary.cli;

import org.metadatacenter.radx.datadictionary.*;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.Callable;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-22
 */
@Component
@Command(name = "validate")
public class ValidateCommand implements Callable<Integer> {

    private final Validator validator;

    private final ValidationReportWriter reportWriter;

    @Option(names = "--in", description = "A path to a data dictionary file or a directory that directly or indirectly contains data dictionary files")
    protected Path in;

    @Option(names = "--out", description = "A path to an output file where a validation report will be written.  This is optional.  If it is not provided then the report will be written to stdout.")
    protected Path out;

    @Option(names = "--format", description = "The report output format", defaultValue = "TSV", showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
    protected ValidationReportFormat format = ValidationReportFormat.TSV;

    public ValidateCommand(Validator validator, ValidationReportWriter reportWriter) {
        this.validator = validator;
        this.reportWriter = reportWriter;
    }

    private OutputStream getOutputStream() throws IOException {
        if(out != null) {
            return Files.newOutputStream(out);
        }
        else {
            return System.out;
        }
    }

    @Override
    public Integer call() throws Exception {

        var out = getOutputStream();
        reportWriter.writeReportHeader(out, format);

        if(Files.isDirectory(in)) {
            Files.walkFileTree(in, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if(!Files.isHidden(file) && file.getFileName().toString().endsWith(".csv")) {
                        processFile(file, out);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        else {
            processFile(in, out);
        }
        return 0;
    }

    private void processFile(Path pth, OutputStream out) throws IOException {
        var csv = parseCsv(pth);
        var report = validator.validateDataDictionary(csv);
        reportWriter.writeReport(report, pth, out, format);
    }




    private static Csv parseCsv(Path pth) throws IOException {
        try (var inputStream = Files.newInputStream(pth)) {
            return new CsvParser().parseCsv(inputStream);
        }
    }

}
