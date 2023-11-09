# radx-data-dictionary-validator

A validator for radx data dictionaries.

# Installation 

To install download [ddv.zip](https://github.com/bmir-radx/radx-data-dictionary-validator/releases/download/v1.1.0/ddv.zip) and then unzip.  Move the extracted `ddv` tool to into a location on your executable path.  

# Examples

To use, type `ddv --help` for a list of options.

RADx Data Dictionaries should be in the RADx Data Dictionary format, which is a CSV based format that is described [here](https://github.com/bmir-radx/radx-data-dictionary-specification/blob/main/radx-data-dictionary-specification.md).  

To validate a data dictionary called `mydictionary.csv`, type `ddv --in mydictionary.csv`.  The validation report will be written to stdout in TSV format.

To output CSV format add the `--format CSV` flag.  For example,

`ddv --in mydictionary.csv --format CSV`

To write to a file, you can either, redirect output, for example,

`ddv --in mydictionary.csv --format CSV > myreport.csv`

or you can specify the `--out` option, for example,

`ddv --in mydictionary.csv --format CSV --out myreport.csv`

# Programmatic Use

The data dictionary validator library is compatible with Spring Boot. To use in your app add the following Maven dependency.

```xml
<dependency>
            <groupId>edu.stanford.bmir.radx</groupId>
            <artifactId>radx-data-dictionary-validator-lib</artifactId>
            <version>1.1.0</version>
</dependency>
```

Add `edu.stanford.bmir.radx.datadictionary` to the list of packages that are scanned, for example,

```java
@SpringBootApplication
@ComponentScan(basePackages = "edu.stanford.bmir.radx.datadictionary")
MyApp { ... }
```

Next, inject the `Validator` bean where you need it.  For example,

```java
@Component
public class ValidateIt {

    private final Validator validator;

    public ValidateIt(Validator validator) {
        this.validator = validator;
    }

    public void doValidation(InputStream in) throws IOException {

        // Parse our data dictionary csv
        var parser = new CsvParser();
        var csv = parser.parseCsv(in);

        // Validate the data dictionary
        var validationReport = validator.validateDataDictionary(csv);
        var validationResults = validationReport.results();

        // Process the validation report as needed
        var errorCount = validationResults.stream()
                .filter(result -> result.validationLevel().equals(ValidationLevel.ERROR))
                .count();

        System.out.println("There are " + errorCount + " errors");

        // Each report contains a list of results
        validationReport.results()
                .forEach(validationResult -> {
                    // For each validation result we have:

                    // Validation level:  ERROR, WARNING, INFO.  WARNING and INFO provide
                    // guidance on how to clean up a data dictionary
                    var validationLevel = validationResult.validationLevel();

                    // The zero-based index of the row in the data dictionary CSV that
                    // the result pertains to
                    // Note that 0 is the header row
                    var rowIndex = validationResult.getRowNumber();

                    // The complete row that the result pertains to
                    var row = validationResult.csvRow();

                    // The human-readable name of the validation result
                    var name = validationResult.name();

                    // The human-readable message of the validation result
                    var message = validationResult.message();

                    // The subject of the validation result.  This is the string (cell value)
                    // that the result pertains to
                    var subject = validationResult.subject();

                });
    }
}
```

