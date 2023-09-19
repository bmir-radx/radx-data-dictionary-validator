package edu.stanford.bmir.radx.datadictionary.lib;

import jakarta.annotation.Nonnull;

public record MalformedPatternResult(CsvRow csvRow,
                                     String pattern,
                                     String errorMessage,
                                     int errorPosition) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Nonnull
    @Override
    public String message() {
        return errorMessage + " at index " + errorPosition;
    }

    @Nonnull
    @Override
    public String name() {
        return "Malformed pattern";
    }

    @Nonnull
    @Override
    public String subject() {
        return pattern;
    }
}
