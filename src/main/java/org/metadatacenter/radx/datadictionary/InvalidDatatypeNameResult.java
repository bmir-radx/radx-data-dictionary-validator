package org.metadatacenter.radx.datadictionary;

public record InvalidDatatypeNameResult(CsvRow csvRow, String datatypeName) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Override
    public String message() {
        return "Invalid datatype name: " + datatypeName;
    }
}
