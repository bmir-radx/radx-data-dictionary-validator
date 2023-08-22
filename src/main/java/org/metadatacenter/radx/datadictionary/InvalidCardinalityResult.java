package org.metadatacenter.radx.datadictionary;

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

    @Override
    public String message() {
        return "Invalid cardinality value: " + value + ".  Expected either 'single' or 'multiple'.";
    }

}
