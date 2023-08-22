package org.metadatacenter.radx.datadictionary;

import jakarta.annotation.Nonnull;

public record IdNotPresentResult(CsvRow csvRow) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Nonnull
    @Override
    public String message() {
        return "Missing Id";
    }

    @Nonnull
    @Override
    public String name() {
        return "Id not present";
    }

    @Nonnull
    @Override
    public String subject() {
        return "";
    }
}
