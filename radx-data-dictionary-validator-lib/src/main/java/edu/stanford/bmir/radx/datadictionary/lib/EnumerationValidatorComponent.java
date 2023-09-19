package edu.stanford.bmir.radx.datadictionary.lib;

import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.function.Consumer;

import static edu.stanford.bmir.radx.datadictionary.lib.JavaCcUtil.getParseExceptionMessage;
import static edu.stanford.bmir.radx.datadictionary.lib.JavaCcUtil.getTokenManagerErrorMessage;


/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 */
@Component
public class EnumerationValidatorComponent implements ValidatorComponent {

    @Override
    public void validate(Csv csv, Consumer<ValidationResult> handler) {
        var enumerationColumnIndex = csv.getIndex("Enumeration");
        if(enumerationColumnIndex == -1) {
            return;
        }

        for(int i = 0; i < csv.dataRows().size(); i++) {
            var row = csv.dataRows().get(i);
            var enumeration = row.get(enumerationColumnIndex);
            if(!enumeration.isBlank()) {
                try {
                    new EnumerationParser(new StringReader(enumeration)).Enumeration();
                } catch (ParseException e) {
                    handler.accept(new MalformedEnumerationErrorResult(row,
                                                                       enumeration,
                                                                       getParseExceptionMessage(e)));
                } catch (TokenMgrError e) {
                    handler.accept(new MalformedEnumerationErrorResult(row,
                                                                       enumeration,
                                                                       getTokenManagerErrorMessage(e)));
                }

            }
        }
    }


}
