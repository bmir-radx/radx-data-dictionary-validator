package edu.stanford.bmir.radx.datadictionary.lib;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DataDictionaryColumnDescriptor(@JsonProperty("name") String name,
                                             @JsonProperty("valueStatus") ValueStatus valueStatus) {

}
