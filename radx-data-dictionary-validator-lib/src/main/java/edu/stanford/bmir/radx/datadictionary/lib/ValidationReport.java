package edu.stanford.bmir.radx.datadictionary.lib;

import java.util.List;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-01-31
 */
public record ValidationReport(Csv csv, List<ValidationResult> results) {

}
