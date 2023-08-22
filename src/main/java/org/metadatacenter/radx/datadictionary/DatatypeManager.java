package org.metadatacenter.radx.datadictionary;

import jakarta.annotation.Nonnull;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 */
public class DatatypeManager {

    private final Map<String, DatatypeSpec> datatypeNames;

    private final Map<String, String> suggestedFixes = Map.of(
            "bit", "boolean",
            "text", "string",
            "number", "decimal",
            "email", "string",
            "zipcode", "string",
            "phone", "string"
    );

    public static DatatypeManager from(Set<DatatypeSpec> datatypes) {
        var map = datatypes.stream()
                .collect(Collectors.toMap(DatatypeSpec::name, datatypeSpec -> datatypeSpec));
        return new DatatypeManager(map);
    }

    private DatatypeManager(Map<String, DatatypeSpec> byName) {
        this.datatypeNames = byName;
    }

    public boolean isValidDatatypeName(@Nonnull String datatypeName) {
        return this.datatypeNames.containsKey(datatypeName);
    }

    /**
     * Suggests a correction to a datatype name.
     * @param datatypeName The datatype name to be corrected.
     * @return The corrected datatype name
     */
    public String getSuggestedName(String datatypeName) {
        if(datatypeNames.containsKey(datatypeName)) {
            return "";
        }
        return datatypeNames.keySet()
                .stream()
                .filter(name -> name.equalsIgnoreCase(datatypeName))
                .findFirst()
                .orElseGet(
                        () -> suggestedFixes.getOrDefault(datatypeName.toLowerCase(), "")
                );
    }
}
