package edu.stanford.bmir.radx.datadictionary.lib;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-11-14
 */
public enum Cardinality {

    @JsonEnumDefaultValue
    @JsonProperty("single")
    SINGLE,

    @JsonProperty("multiple")
    MULTIPLE;

    public static Cardinality of(String cardinality) {
        if(cardinality.equalsIgnoreCase("multiple")) {
            return MULTIPLE;
        }
        else {
            return SINGLE;
        }
    }
}
