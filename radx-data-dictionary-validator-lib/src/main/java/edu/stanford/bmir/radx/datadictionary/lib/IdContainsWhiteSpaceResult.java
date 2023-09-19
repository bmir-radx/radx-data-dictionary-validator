package edu.stanford.bmir.radx.datadictionary.lib;

import jakarta.annotation.Nonnull;

public record IdContainsWhiteSpaceResult(CsvRow csvRow,
                                         String id) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.INFO;
    }

    @Nonnull
    @Override
    public String name() {
        return "Id contains white space";
    }

    @Nonnull
    @Override
    public String subject() {
        return id;
    }

    @Nonnull
    @Override
    public String message() {
        return "";
    }
}
