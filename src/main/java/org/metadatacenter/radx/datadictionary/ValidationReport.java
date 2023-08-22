package org.metadatacenter.radx.datadictionary;

import java.util.List;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-01-31
 */
public record ValidationReport(Csv csv, List<ValidationResult> results) {

}
