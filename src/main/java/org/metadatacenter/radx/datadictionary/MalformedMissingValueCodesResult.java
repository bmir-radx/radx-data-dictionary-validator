package org.metadatacenter.radx.datadictionary;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 */
public record MalformedMissingValueCodesResult(CsvRow csvRow, String errorMessage) implements ValidationResult{

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Override
    public String message() {
        return "Malformed missing values codes: " + errorMessage;
    }
}
