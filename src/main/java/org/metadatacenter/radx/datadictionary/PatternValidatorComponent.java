package org.metadatacenter.radx.datadictionary;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 */
@Component
public class PatternValidatorComponent implements ValidatorComponent{

    @Override
    public void validate(Csv csv, Consumer<ValidationResult> handler) {
        int patternFieldIndex = csv.getIndex(FieldName.PATTERN);
        if(patternFieldIndex == -1) {
            return;
        }
        var dataRows = csv.dataRows();
        dataRows.forEach(dataRow -> {
            var pattern = dataRow.get(patternFieldIndex);
            if(!pattern.isBlank()) {
                try {
                    Pattern.compile(pattern);
                } catch (PatternSyntaxException e) {
                    handler.accept(new MalformedPatternResult(dataRow,
                                                              pattern,
                                                              e.getDescription(),
                                                              e.getIndex()));
                }
            }
        });
    }
}
