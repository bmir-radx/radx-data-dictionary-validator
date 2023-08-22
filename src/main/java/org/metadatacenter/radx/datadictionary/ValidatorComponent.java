package org.metadatacenter.radx.datadictionary;

import java.util.function.Consumer;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-01-31
 */
public interface ValidatorComponent {

    void validate(Csv csv, Consumer<ValidationResult> handler);
}
