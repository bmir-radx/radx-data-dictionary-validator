package org.metadatacenter.radx.datadictionary;

public record MalformedPatternResult(CsvRow csvRow,
                                     String pattern,
                                     String errorMessage,
                                     int errorPosition) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Override
    public String message() {
        return "Malformed regular expression pattern: " + errorMessage + " at " + errorPosition;
    }
}
