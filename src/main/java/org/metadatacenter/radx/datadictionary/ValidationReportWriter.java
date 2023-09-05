package org.metadatacenter.radx.datadictionary;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-22
 */
@Component
public class ValidationReportWriter {

    public void writeReportHeader(OutputStream outputStream, ValidationReportFormat reportFormat) throws IOException {
        var csvFormat = getCsvFormat(reportFormat);
        var resultWriter = new CSVPrinter(new PrintStream(outputStream, true, StandardCharsets.UTF_8), csvFormat);
        resultWriter.printRecord("Directory", "File", "Row", "Level", "Problem", "Message", "Value");
    }

    public void writeReport(ValidationReport report,
                            Path dataDictionaryPath,
                            OutputStream outputStream,
                            ValidationReportFormat reportFormat,
                            Set<ValidationLevel> levels) throws IOException {

        var csvFormat = getCsvFormat(reportFormat);

        var resultWriter = new CSVPrinter(new PrintStream(outputStream, true, StandardCharsets.UTF_8), csvFormat);

        report.results()
              .stream()
              .filter(r -> levels.contains(r.validationLevel())).map(r -> toCsvRecord(dataDictionaryPath, r))
              .forEach(r -> {
                  try {
                      resultWriter.printRecord(r);
                  } catch (IOException e) {
                      throw new UncheckedIOException(e);
                  }
              });

    }



    private static List<? extends Serializable> toCsvRecord(Path pth, ValidationResult r) {
        var fileName = pth.getFileName().toString();
        var phs = pth.getParent().getFileName().toString();
        var rowIndex = r.csvRow().rowIndex();
        var rowNumber = rowIndex + 1;
        var validationLevel = r.validationLevel().name();
        return List.of(phs, fileName, rowNumber, validationLevel, r.name(), r.message(), r.subject());
    }

    private CSVFormat getCsvFormat(ValidationReportFormat reportFormat) {
        if(reportFormat.equals(ValidationReportFormat.CSV)) {
            return CSVFormat.DEFAULT;
        }
        else {
            return CSVFormat.TDF;
        }
    }
}
