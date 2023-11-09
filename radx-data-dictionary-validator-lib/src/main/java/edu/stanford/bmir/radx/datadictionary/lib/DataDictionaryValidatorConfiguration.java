package edu.stanford.bmir.radx.datadictionary.lib;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-11-09
 */
@Configuration
public class DataDictionaryValidatorConfiguration {

    @Bean
    DataDictionarySpec dataDictionarySpec(ObjectMapper mapper) {
        try {
            var csvMapper = new CsvMapper();
            var inputStream = DataDictionarySpec.class.getResourceAsStream("/columns.csv");
            MappingIterator<DataDictionaryColumnDescriptor> iterator = csvMapper.readerWithSchemaFor(DataDictionaryColumnDescriptor.class)
                                                                                .readValues(inputStream);
            var rows = iterator.readAll();
            return new DataDictionarySpec(rows);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    DatatypeManager datatypeManager(DatatypeManagerFactory datatypeManagerFactory) {
        return datatypeManagerFactory.get();
    }

}
