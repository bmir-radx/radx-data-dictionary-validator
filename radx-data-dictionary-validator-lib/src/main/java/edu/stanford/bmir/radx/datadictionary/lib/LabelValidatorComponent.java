package edu.stanford.bmir.radx.datadictionary.lib;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-18
 */
@Component
public class LabelValidatorComponent implements ValidatorComponent {

    @Override
    public void validate(Csv csv, Consumer<ValidationResult> handler) {
        int labelIndex = csv.getIndex(FieldName.LABEL);
        if(labelIndex == -1) {
            return;
        }
        for(var row : csv.dataRows()) {
            if (labelIndex < row.size()) {
                var label = row.get(labelIndex);
                if (label.isBlank()) {
                    handler.accept(new LabelNotPresentResult(row));
                }
            }
            else {
                handler.accept(new LabelNotPresentResult(row));
            }
        }
    }
}
