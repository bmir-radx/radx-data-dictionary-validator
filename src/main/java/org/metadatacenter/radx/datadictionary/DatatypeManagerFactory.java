package org.metadatacenter.radx.datadictionary;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 */
@Component
public class DatatypeManagerFactory {

    public DatatypeManager get() {
        try {
            var datatypesIs = DatatypeManager.class.getResourceAsStream("/datatypes.csv");
            var datatypesReader = new BufferedReader(new InputStreamReader(datatypesIs));
            var csvParser = new CSVParser(datatypesReader, CSVFormat.DEFAULT);
            var datatypes = csvParser.getRecords()
                    .stream().map(rec -> {
                        try {
                            return new DatatypeSpec(rec.get(0), Pattern.compile(rec.get(1)));
                        } catch (Exception e) {
                            throw new RuntimeException("Error when processing " + rec + ".  " + e.getMessage());
                        }

                    })
                    .collect(Collectors.toSet());
            return DatatypeManager.from(datatypes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
