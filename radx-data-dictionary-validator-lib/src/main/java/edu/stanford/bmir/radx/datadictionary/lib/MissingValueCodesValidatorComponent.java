package edu.stanford.bmir.radx.datadictionary.lib;

import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.function.Consumer;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 */
@Component
public class MissingValueCodesValidatorComponent implements ValidatorComponent {

    @Override
    public void validate(Csv csv, Consumer<ValidationResult> handler) {
        int index = csv.getIndex(FieldName.MISSING_VALUE_CODES);
        if(index == -1) {
            return;
        }
        csv.dataRows()
                .forEach(row -> {
                    var value = row.get(index);
                    if (!value.isBlank()) {
                        try {
                            new EnumerationParser(new StringReader(value))
                                    .Enumeration();
                        } catch (ParseException e) {
                            handler.accept(new MalformedMissingValueCodesResult(row, value, JavaCcUtil.getParseExceptionMessage(e)));
                        } catch (TokenMgrError e) {
                            handler.accept(new MalformedMissingValueCodesResult(row, value, JavaCcUtil.getTokenManagerErrorMessage(e)));
                        }
                    }
                });
    }
}
