package org.metadatacenter.radx.datadictionary;

import java.util.regex.Pattern;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 */
public record DatatypeSpec(String name, Pattern regex) {

}
