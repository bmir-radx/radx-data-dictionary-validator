package org.metadatacenter.radx.datadictionary;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-01-31
 */
@Component
public class Validator {

    private final RequiredHeadersValidatorComponent headersValidatorComponent;

    private final IdFieldValidatorComponent idFieldValidatorComponent;

    private final DatatypeValidatorComponent datatypeValidatorComponent;

    private final LabelValidatorComponent labelFieldValidatorComponent;

    private final EnumerationValidatorComponent enumerationValidatorComponent;

    private final PatternValidatorComponent patternValidatorComponent;

    private final CardinalityValidatorComponent cardinalityValidatorComponent;

    private final MissingValueCodesValidatorComponent missingValueCodesValidatorComponent;

    private final SeeAlsoValidatorComponent seeAlsoValidatorComponent;

    public Validator(RequiredHeadersValidatorComponent headersValidatorComponent,
                     IdFieldValidatorComponent idFieldValidatorComponent,
                     DatatypeValidatorComponent datatypeValidatorComponent,
                     LabelValidatorComponent labelFieldValidatorComponent,
                     EnumerationValidatorComponent enumerationValidatorComponent,
                     PatternValidatorComponent patternValidatorComponent,
                     CardinalityValidatorComponent cardinalityValidatorComponent,
                     MissingValueCodesValidatorComponent missingValueCodesValidatorComponent,
                     SeeAlsoValidatorComponent seeAlsoValidatorComponent) {
        this.headersValidatorComponent = headersValidatorComponent;
        this.idFieldValidatorComponent = idFieldValidatorComponent;
        this.datatypeValidatorComponent = datatypeValidatorComponent;
        this.labelFieldValidatorComponent = labelFieldValidatorComponent;
        this.enumerationValidatorComponent = enumerationValidatorComponent;
        this.patternValidatorComponent = patternValidatorComponent;
        this.cardinalityValidatorComponent = cardinalityValidatorComponent;
        this.missingValueCodesValidatorComponent = missingValueCodesValidatorComponent;
        this.seeAlsoValidatorComponent = seeAlsoValidatorComponent;
    }

    public ValidationReport validateDataDictionary(Csv csv) {
        var messages = new ArrayList<ValidationResult>();
        Consumer<ValidationResult> consumer = messages::add;
        // Check headings are present
        headersValidatorComponent.validate(csv, consumer);

        // For each row
        // Check Id is present
        idFieldValidatorComponent.validate(csv, consumer);
        // Warn if label is not present
        labelFieldValidatorComponent.validate(csv, consumer);

        // Check Datatype (required)
        datatypeValidatorComponent.validate(csv, consumer);

        cardinalityValidatorComponent.validate(csv, consumer);

        // Check pattern (validates as regex)
        patternValidatorComponent.validate(csv, consumer);
        // Check units (UCUM?)

        // Validate enumerations
        enumerationValidatorComponent.validate(csv, consumer);

        seeAlsoValidatorComponent.validate(csv, consumer);

        missingValueCodesValidatorComponent.validate(csv, consumer);



        var comparator = Comparator.comparing(ValidationResult::getRowNumber)
                          .thenComparing(ValidationResult::validationLevel)
                          .thenComparing(ValidationResult::message);
        messages.sort(comparator);
        return new ValidationReport(csv, messages);
    }

}
