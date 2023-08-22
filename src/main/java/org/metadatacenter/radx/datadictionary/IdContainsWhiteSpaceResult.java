package org.metadatacenter.radx.datadictionary;

public record IdContainsWhiteSpaceResult(CsvRow csvRow,
                                         String id) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.WARNING;
    }

    @Override
    public String message() {
        return "Id contains white space";
    }
}
