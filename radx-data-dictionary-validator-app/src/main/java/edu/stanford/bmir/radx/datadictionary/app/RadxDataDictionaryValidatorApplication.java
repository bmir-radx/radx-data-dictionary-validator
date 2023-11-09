package edu.stanford.bmir.radx.datadictionary.app;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import edu.stanford.bmir.radx.datadictionary.app.cli.ValidateCommand;
import edu.stanford.bmir.radx.datadictionary.lib.DataDictionaryColumnDescriptor;
import edu.stanford.bmir.radx.datadictionary.lib.DataDictionarySpec;
import edu.stanford.bmir.radx.datadictionary.lib.DatatypeManager;
import edu.stanford.bmir.radx.datadictionary.lib.DatatypeManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import picocli.CommandLine;

import java.io.*;

@SpringBootApplication(scanBasePackages = "edu.stanford.bmir.radx.datadictionary")
public class RadxDataDictionaryValidatorApplication implements CommandLineRunner, ExitCodeGenerator {

    private final CommandLine.IFactory iFactory;

    @Autowired
    private ApplicationContext applicationContext;

    private int exitCode;

    public RadxDataDictionaryValidatorApplication(CommandLine.IFactory iFactory) {
        this.iFactory = iFactory;
    }

    public static void main(String[] args) {
        SpringApplication.run(RadxDataDictionaryValidatorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        var validateCommand = applicationContext.getBean(ValidateCommand.class);
        exitCode = new CommandLine(validateCommand, iFactory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
