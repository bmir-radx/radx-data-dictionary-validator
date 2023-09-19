package edu.stanford.bmir.radx.datadictionary.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-09-19
 */
@SpringBootTest
public class ContextLoadsTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void shouldCreateApplication() {

    }
}
