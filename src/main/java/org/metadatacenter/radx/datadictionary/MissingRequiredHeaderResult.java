package org.metadatacenter.radx.datadictionary;

import java.util.List;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-02-02
 */
public record MissingRequiredHeaderResult(CsvRow csvRow,
                                          String headerName) implements ValidationResult {

    public static MissingRequiredHeaderResult get(CsvRow csvRow,
                                                  String headerName) {
        return new MissingRequiredHeaderResult(csvRow, headerName);
    }

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Override
    public String message() {
        return "Missing required field: " + headerName;
    }
}
