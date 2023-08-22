package org.metadatacenter.radx.datadictionary;

public record MissingIdResult(CsvRow csvRow) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Override
    public String message() {
        return "Missing Id";
    }
}
