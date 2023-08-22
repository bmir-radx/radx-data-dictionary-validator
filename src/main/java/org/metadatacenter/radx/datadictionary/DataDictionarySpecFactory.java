package org.metadatacenter.radx.datadictionary;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 */
@Component
public class DataDictionarySpecFactory {

    @Nonnull
    public DataDictionarySpec getDictionarySpec() {
        try {
            var csvMapper = new CsvMapper();
            var inputStream = RadxDataDictionaryValidatorApplication.class.getResourceAsStream("/columns.csv");
            MappingIterator<DataDictionaryColumnDescriptor> iterator = csvMapper.readerWithSchemaFor(DataDictionaryColumnDescriptor.class)
                                                                                .readValues(inputStream);
            var rows = iterator.readAll();
            return new DataDictionarySpec(rows);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
