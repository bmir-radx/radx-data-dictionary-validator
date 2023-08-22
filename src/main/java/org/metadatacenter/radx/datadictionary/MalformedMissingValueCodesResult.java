package org.metadatacenter.radx.datadictionary;

import jakarta.annotation.Nonnull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 */
public record MalformedMissingValueCodesResult(CsvRow csvRow, String codes, String errorMessage) implements ValidationResult{

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Nonnull
    @Override
    public String message() {
        return errorMessage;
    }

    @Nonnull
    @Override
    public String name() {
        return "Malformed missing value codes";
    }

    @Nonnull
    @Override
    public String subject() {
        return codes;
    }
}
