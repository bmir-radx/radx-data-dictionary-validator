package org.metadatacenter.radx.datadictionary;

import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeFactory;
import java.util.function.Consumer;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-18
 */
@Component
public class DatatypeValidatorComponent implements ValidatorComponent {

    private final DatatypeManager datatypeManager;

    public DatatypeValidatorComponent(DatatypeManager datatypeManager) {
        this.datatypeManager = datatypeManager;
    }

    @Override
    public void validate(Csv csv, Consumer<ValidationResult> handler) {
        int datatypeFieldIndex = csv.getIndex(FieldName.DATATYPE);
        if(datatypeFieldIndex == -1) {
            return;
        }
        csv.dataRows()
                .forEach(row -> checkIdOnRow(row, handler, datatypeFieldIndex));
    }

    private void checkIdOnRow(CsvRow row, Consumer<ValidationResult> handler, int dataTypeFieldIndex) {
        if (dataTypeFieldIndex >= row.size()) {
            handler.accept(new MissingDatatypeResult(row));
            return;
        }
        var datatypeName = row.get(dataTypeFieldIndex);
        if(datatypeName.isBlank()) {
            handler.accept(new MissingDatatypeResult(row));
        }
        else if(!datatypeManager.isValidDatatypeName(datatypeName)) {
            handler.accept(new InvalidDatatypeNameResult(row, datatypeName));
        }
        DatatypeFactory df;

    }
}
