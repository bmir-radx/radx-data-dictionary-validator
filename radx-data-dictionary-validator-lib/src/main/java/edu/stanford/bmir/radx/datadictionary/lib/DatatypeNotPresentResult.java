package edu.stanford.bmir.radx.datadictionary.lib;

import jakarta.annotation.Nonnull;

public record DatatypeNotPresentResult(CsvRow csvRow) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Nonnull
    @Override
    public String name() {
        return "Datatype name not present";
    }

    @Nonnull
    @Override
    public String subject() {
        return "";
    }

    @Nonnull
    @Override
    public String message() {
        return "Blank values for datatype names are not allowed";
    }
}
