package edu.stanford.bmir.radx.datadictionary.app;

import edu.stanford.bmir.radx.datadictionary.lib.Csv;
import edu.stanford.bmir.radx.datadictionary.lib.CsvRow;
import edu.stanford.bmir.radx.datadictionary.lib.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-09-19
 */
@SpringBootTest
public class ContextLoadsTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Validator validator;

    @Test
    public void shouldCreateApplication() {

    }

    @Test
    void shouldCreateValidator() {

    }

    @Test
    void shouldValidate() {
        var report = validator.validateDataDictionary(new Csv(new CsvRow(0, List.of("Id", "Label")),
                                                 List.of())
                                         );
        assertThat(report).isNotNull();
    }
}
