package edu.stanford.bmir.radx.datadictionary.lib;

import java.util.List;

/**
 *
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-01-31
 * <p>
 * The `ValidationReport` class represents the result of validating a RADx CSV data dictionary.
 * It contains information about the original CSV data, as well as a list of validation results.
 *
 * @param csv The CSV data dictionary that was validated.
 * @param results A list of {@link ValidationResult} instances representing the validation results.
 */
public record ValidationReport(Csv csv, List<ValidationResult> results) {

}
