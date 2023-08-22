package org.metadatacenter.radx.datadictionary;

import jakarta.annotation.Nonnull;

public record MalformedSeeAlsoUrlResult(CsvRow csvRow, String value, String errorMessage, int errorIndex) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Nonnull
    @Override
    public String message() {
        return errorMessage + " at index " + errorIndex;
    }

    @Nonnull
    @Override
    public String name() {
        return "Malformed SeeAlso URL";
    }

    @Nonnull
    @Override
    public String subject() {
        return value;
    }
}
