package edu.stanford.bmir.radx.datadictionary.lib;

import jakarta.annotation.Nonnull;

public record UnknownDatatypeNameResult(CsvRow csvRow, String datatypeName, String suggestedName) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Nonnull
    @Override
    public String message() {
        return datatypeName + " is not an XML Schema Datatype name.  Did you mean " + suggestedName + "?";
    }

    @Nonnull
    @Override
    public String name() {
        return "Unknown datatype name";
    }

    @Nonnull
    @Override
    public String subject() {
        return datatypeName;
    }
}
