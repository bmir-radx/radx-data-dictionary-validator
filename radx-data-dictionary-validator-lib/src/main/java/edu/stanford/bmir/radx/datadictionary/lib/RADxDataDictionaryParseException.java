package edu.stanford.bmir.radx.datadictionary.lib;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-11-14
 */
public class RADxDataDictionaryParseException extends Exception {

    private final ValidationResult error;

    public RADxDataDictionaryParseException(ValidationResult error) {
        super(error.message());
        this.error = error;
    }

    public ValidationResult getError() {
        return error;
    }
}
