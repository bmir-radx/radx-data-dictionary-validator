package org.metadatacenter.radx.datadictionary;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-18
 */
@Component
public class IdFieldValidatorComponent implements ValidatorComponent {

    @Override
    public void validate(Csv csv, Consumer<ValidationResult> handler) {
        int idIndex = csv.getIndex("Id");
        if(idIndex == -1) {
            return;
        }
        csv.dataRows().forEach(row -> checkIdOnRow(csv, handler, row, idIndex));
    }

    private static void checkIdOnRow(Csv csv, Consumer<ValidationResult> handler, CsvRow row, int idIndex) {
        if (idIndex >= row.size()) {
            handler.accept(new MissingIdResult(row));
            return;
        }
        var id = row.get(idIndex);
        if(id.isBlank()) {
            handler.accept(new MissingIdResult(row));
        }
        else {
            if(id.startsWith(" ")) {
                handler.accept(new IdStartsWithWhiteSpaceResult(row, id));
            }
           if(id.contains(" ")) {
               handler.accept(new IdContainsWhiteSpaceResult(row, id));
           }
        }
    }
}
