package edu.stanford.bmir.radx.datadictionary.lib;

import java.util.regex.Pattern;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 */
public record DatatypeSpec(String name, Pattern regex) {

}
