package org.metadatacenter.radx.datadictionary;

public record IdStartsWithWhiteSpaceResult(CsvRow csvRow,
                                           String id) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.WARNING;
    }

    @Override
    public String message() {
        return "Id starts with white space";
    }
}
