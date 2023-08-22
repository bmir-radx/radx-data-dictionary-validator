package org.metadatacenter.radx.datadictionary;

import org.metadatacenter.radx.radxdatadictionary.EnumerationParser;
import org.metadatacenter.radx.radxdatadictionary.ParseException;
import org.metadatacenter.radx.radxdatadictionary.TokenMgrError;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.function.Consumer;

import static org.metadatacenter.radx.datadictionary.JavaCcUtil.getParseExceptionMessage;
import static org.metadatacenter.radx.datadictionary.JavaCcUtil.getTokenManagerErrorMessage;


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
