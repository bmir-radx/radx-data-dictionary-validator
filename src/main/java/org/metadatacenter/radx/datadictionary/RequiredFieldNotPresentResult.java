package org.metadatacenter.radx.datadictionary;

import jakarta.annotation.Nonnull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-02-02
 */
public record RequiredFieldNotPresentResult(CsvRow csvRow,
                                            String fieldName) implements ValidationResult {

    public static RequiredFieldNotPresentResult get(CsvRow csvRow,
                                                    String fieldName) {
        return new RequiredFieldNotPresentResult(csvRow, fieldName);
    }

    @Override
    public ValidationLevel validationLevel() {
        return ValidationLevel.ERROR;
    }

    @Nonnull
    @Override
    public String name() {
        return "Required field not present";
    }

    @Nonnull
    @Override
    public String subject() {
        return fieldName;
    }

    @Nonnull
    @Override
    public String message() {
        var suggestion = csvRow.rowData()
                .stream()
                .filter(f -> f.equalsIgnoreCase(fieldName))
                .map(f -> "A field (column) called " + f + " is present.  Should " + f + " be renamed to " + fieldName + "?")
                .findFirst()
                .map(sug -> ". " + sug)
                .orElse("");
        return fieldName + " is a required field (column) but it is not present" + suggestion;
    }
}
