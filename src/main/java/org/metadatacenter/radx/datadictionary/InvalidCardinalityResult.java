package org.metadatacenter.radx.datadictionary;

import jakarta.annotation.Nonnull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 */
public record InvalidCardinalityResult(CsvRow csvRow, String value) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Nonnull
    @Override
    public String message() {
        return "Expected either 'single' or 'multiple'";

    }

    @Nonnull
    @Override
    public String name() {
        return "Invalid cardinality value";
    }

    @Nonnull
    @Override
    public String subject() {
        return value;
    }
}
