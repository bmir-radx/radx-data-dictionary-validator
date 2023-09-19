package edu.stanford.bmir.radx.datadictionary.lib;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 */
public enum FieldName {

    ID("Id"),

    LABEL("Label"),

    DESCRIPTION("Description"),

    DATATYPE("Datatype"),

    PATTERN("Pattern"),

    ENUMERATION("Enumeration"),

    CARDINALITY("Cardinality"),

    MISSING_VALUE_CODES("MissingValueCodes"),

    SEE_ALSO("SeeAlso");

    private final String headerValue;

    FieldName(String headerValue) {
        this.headerValue = headerValue;
    }

    public String headerValue() {
        return headerValue;
    }
}

