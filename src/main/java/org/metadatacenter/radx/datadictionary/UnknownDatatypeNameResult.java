package org.metadatacenter.radx.datadictionary;

public record UnknownDatatypeNameResult(CsvRow csvRow, String datatypeName) implements ValidationResult {

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Override
    public String message() {
        return "Unknown datatype name: " + datatypeName;
    }
}
