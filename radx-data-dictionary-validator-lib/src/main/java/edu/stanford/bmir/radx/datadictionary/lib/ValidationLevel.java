package edu.stanford.bmir.radx.datadictionary.lib;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-01-31
 * <p>
 * The `ValidationLevel` enum represents different levels of severity for validation results.
 * It is used to categorize validation outcomes into error, warning, or informational levels.
 */
public enum ValidationLevel {

    /**
     * Indicates an error level validation result.
     * Errors represent critical issues that need to be addressed.  A RADx data dictionary
     * that contains errors is invalid.  Errors MUST be addressed before a data dictionary
     * is published.
     */
    ERROR,

    /**
     * Indicates a warning level validation result.
     * Warnings represent non-critical issues that may require attention.  A RADx data dictionary
     * that contains warnings is valid, but it can be improved for consumers by addressing the warnings.  Generally
     * speaking, warnings SHOULD be addressed before the data dictionary is published.
     */
    WARNING,

    /**
     * Indicates an informational level validation result.
     * Informational messages provide additional details without indicating an issue. A RADx data dictionary
     * that contains info messages is valid, but it can be improved by addressing the info messages.
     */
    INFO
}
