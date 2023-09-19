package edu.stanford.bmir.radx.datadictionary.lib;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 */
@Component
public class CardinalityValidatorComponent implements ValidatorComponent {

    protected static final String SINGLE = "single";

    protected static final String MULTIPLE = "multiple";

    @Override
    public void validate(Csv csv, Consumer<ValidationResult> handler) {
        int index = csv.getIndex(FieldName.CARDINALITY);
        if(index == -1) {
            return;
        }
        csv.dataRows().forEach(row -> {
            var value = row.rowData().get(index);
            if(!value.isBlank() && !(value.equals(SINGLE) || value.equals(MULTIPLE))) {
                handler.accept(new InvalidCardinalityResult(row, value));
            }
        });
    }
}
