package edu.stanford.bmir.radx.datadictionary.lib;

import jakarta.annotation.Nonnull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-19
 */
public record EnumerationChoice(@Nonnull String value, @Nonnull String label, @Nonnull String iri) {

}
