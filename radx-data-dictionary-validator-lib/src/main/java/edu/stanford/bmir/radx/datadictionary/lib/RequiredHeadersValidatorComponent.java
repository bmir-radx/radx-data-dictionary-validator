package edu.stanford.bmir.radx.datadictionary.lib;

import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.function.Consumer;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-02-02
 */
@Component
public class RequiredHeadersValidatorComponent implements HeaderValidatorComponent {

    private DataDictionarySpec dataDictionarySpec;

    public RequiredHeadersValidatorComponent(DataDictionarySpec dataDictionarySpec) {
        this.dataDictionarySpec = dataDictionarySpec;
    }

    @Override
    public void validate(Csv csv, Consumer<ValidationResult> resultHandler) {
        var csvHeaders = new LinkedHashSet<>(csv.headerRow().rowData());
        for (DataDictionaryColumnDescriptor desc : dataDictionarySpec.columnDescriptors()) {
            if (!csvHeaders.contains(desc.name())) {
                // Required but missing
                if(desc.valueStatus().equals(ValueStatus.REQUIRED)) {
                    resultHandler.accept(RequiredFieldNotPresentResult.get(csv.headerRow(),
                                                                           desc.name()));
                }
            }
        }
    }


}
