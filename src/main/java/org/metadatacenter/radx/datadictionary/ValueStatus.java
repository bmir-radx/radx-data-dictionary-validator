package org.metadatacenter.radx.datadictionary;

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
