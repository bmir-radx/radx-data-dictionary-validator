package org.metadatacenter.radx.datadictionary;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-01-31
 */
public interface ValidationResult {

    ValidationLevel validationLevel();

    String message();

    CsvRow csvRow();

    default int getRowNumber() {
        return csvRow().rowIndex() + 1;
    }
}
