package org.metadatacenter.radx.datadictionary;

public record MalformedEnumerationErrorResult(CsvRow csvRow,
                                              String enumeration,
                                              String parseMessage) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Override
    public String message() {
        return "Malformed enumeration: " + parseMessage;
    }
}
