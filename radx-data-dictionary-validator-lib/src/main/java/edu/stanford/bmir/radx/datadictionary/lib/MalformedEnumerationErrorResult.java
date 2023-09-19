package edu.stanford.bmir.radx.datadictionary.lib;

import jakarta.annotation.Nonnull;

public record MalformedEnumerationErrorResult(CsvRow csvRow,
                                              String enumeration,
                                              String parseMessage) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Nonnull
    @Override
    public String name() {
        return "Malformed enumeration";
    }

    @Nonnull
    @Override
    public String subject() {
        return enumeration;
    }

    @Nonnull
    @Override
    public String message() {
        return parseMessage;
    }
}
