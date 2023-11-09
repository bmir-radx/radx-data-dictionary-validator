package edu.stanford.bmir.radx.datadictionary.lib;

import jakarta.annotation.Nonnull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-01-31
 */
/**
 * The `ValidationResult` interface represents a result of a validation check performed on a RADx CSV data dictionary.
 * It provides information about the validation level, error message, name of the validation, subject of validation,
 * associated CSV row, and a utility method to retrieve the row number.
 */
public interface ValidationResult {

    /**
     * Retrieves the validation level of the result.
     *
     * @return The {@link ValidationLevel} indicating the severity of the validation result.
     */
    ValidationLevel validationLevel();

    /**
     * Retrieves the error message associated with the validation result. This is a human-readable string that can be
     * displayed in a user-interface.
     *
     * @return A non-null string representing the error message.
     */
    @Nonnull
    String message();

    /**
     * Retrieves the name of the validation.  This is a human-readable string that can be
     * displayed in a user-interface.
     *
     * @return A non-null string representing the name of the validation.
     */
    @Nonnull
    String name();

    /**
     * Retrieves the subject of the validation.  This is usually the contents of the cell
     * that the validation result pertains to.  This is a human-readable string that can be
     * displayed in a user-interface.
     *
     * @return A non-null string representing the subject of the validation.
     */
    @Nonnull
    String subject();

    /**
     * Retrieves the CSV row associated with the validation result.
     *
     * @return The {@link CsvRow} representing the CSV row associated with the validation result.
     */
    CsvRow csvRow();

    /**
     * Retrieves the row number of the associated CSV row.
     *
     * @return The row index of the CSV row associated with the validation result.  Note
     *         that zero represents the index of the header row in a CSV file.  The index of
     *         the row that pertains to this validation result will therefore always be greater than 0.
     */
    default int getRowNumber() {
        return csvRow().rowIndex() + 1;
    }
}

