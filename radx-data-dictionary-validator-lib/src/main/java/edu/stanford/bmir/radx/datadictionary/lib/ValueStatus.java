package edu.stanford.bmir.radx.datadictionary.lib;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-01-31
 */
public enum ValueStatus {

    @JsonProperty("required")
    REQUIRED,

    @JsonProperty("optional")
    OPTIONAL
}
