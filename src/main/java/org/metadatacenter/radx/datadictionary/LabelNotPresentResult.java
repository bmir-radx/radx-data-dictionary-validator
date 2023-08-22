package org.metadatacenter.radx.datadictionary;

import jakarta.annotation.Nonnull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-18
 */
public record LabelNotPresentResult(CsvRow csvRow) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.WARNING;
    }

    @Nonnull
    @Override
    public String name() {
        return "Label not present";
    }

    @Nonnull
    @Override
    public String subject() {
        return "";
    }

    @Nonnull
    @Override
    public String message() {
        return "";
    }
}
