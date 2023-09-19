package edu.stanford.bmir.radx.datadictionary.lib;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Consumer;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-22
 */
@Component
public class SeeAlsoValidatorComponent implements ValidatorComponent {

    @Override
    public void validate(Csv csv, Consumer<ValidationResult> handler) {
        var fieldIndex = csv.getIndex(FieldName.SEE_ALSO);
        if(fieldIndex == -1) {
            return;
        }
        csv.dataRows()
                .forEach(row -> {
                    var value = row.get(fieldIndex);
                    if (!value.isBlank()) {
                        try {
                            var uri = new URI(value);
                            if(!uri.isAbsolute()) {
                                handler.accept(new MalformedSeeAlsoUrlResult(row, value, "URL is not absolute", 0));
                            }
                        } catch (URISyntaxException e) {
                            handler.accept(new MalformedSeeAlsoUrlResult(row, value, e.getReason(), e.getIndex()));
                        }
                    }
                });
    }
}
