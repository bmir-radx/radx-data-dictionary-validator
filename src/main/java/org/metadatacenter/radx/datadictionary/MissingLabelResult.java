package org.metadatacenter.radx.datadictionary;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-18
 */
public record MissingLabelResult(CsvRow csvRow) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.WARNING;
    }

    @Override
    public String message() {
        return "Missing label";
    }
}
