package org.metadatacenter.radx.datadictionary;

import jakarta.annotation.Nonnull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-01-31
 */
public interface ValidationResult {

    ValidationLevel validationLevel();

    @Nonnull
    String message();

    @Nonnull
    String name();

    @Nonnull
    String subject();

    CsvRow csvRow();

    default int getRowNumber() {
        return csvRow().rowIndex() + 1;
    }
}
