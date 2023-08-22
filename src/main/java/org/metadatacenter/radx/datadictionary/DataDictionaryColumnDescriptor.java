package org.metadatacenter.radx.datadictionary;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DataDictionaryColumnDescriptor(@JsonProperty("name") String name,
                                             @JsonProperty("valueStatus") ValueStatus valueStatus) {

}
