package edu.stanford.bmir.radx.datadictionary.lib;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.net.URI;
import java.util.List;
import java.util.regex.Pattern;

public record RADxDataDictionaryRecord(@Nonnull String id,
                                       @Nonnull String label,
                                       @Nonnull String description,
                                       @Nonnull String section,
                                       @Nonnull List<TermIdentifier> terms,
                                       @Nonnull Cardinality cardinality,
                                       @Nonnull Datatype datatype,
                                       @Nullable Pattern pattern,
                                       @Nullable Unit unit,
                                       @Nullable Enumeration enumeration,
                                       @Nullable Enumeration missingValueCodes,
                                       @Nonnull String notes,
                                       @Nonnull String provenance,
                                       @Nullable URI seeAlso) {
}
