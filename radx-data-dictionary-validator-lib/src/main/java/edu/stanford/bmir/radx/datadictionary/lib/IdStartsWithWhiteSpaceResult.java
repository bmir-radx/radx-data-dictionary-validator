package edu.stanford.bmir.radx.datadictionary.lib;

import jakarta.annotation.Nonnull;

public record IdStartsWithWhiteSpaceResult(CsvRow csvRow,
                                           String id) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Nonnull
    @Override
    public String name() {
        return "Id starts with white space";
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
