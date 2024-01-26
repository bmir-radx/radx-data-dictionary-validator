package edu.stanford.bmir.radx.datadictionary.lib;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-11-14
 */
@Component
public class RADxDataDictionaryParser {

    private final Validator validator;

    public RADxDataDictionaryParser(Validator validator) {
        this.validator = validator;
    }

    /**
     * Parses a RADx data dictionary from the provided input stream.
     *
     * @param inputStream The input stream containing the RADx data dictionary information in CSV format. Must not be null.
     * @return A RADxDataDictionary object representing the parsed data dictionary.
     * @throws IOException  If an I/O error occurs while reading from the input stream.
     * @throws RADxDataDictionaryParseException If an error occurs during parsing the RADx data dictionary from the CSV data.
     * @throws NullPointerException If the provided input stream is null.
     */
    @Nonnull
    public RADxDataDictionary parse(@Nonnull InputStream inputStream,
                                    ParseMode parseMode) throws IOException, RADxDataDictionaryParseException {
        Objects.requireNonNull(inputStream);
        var csvParser = new CsvParser();
        var csv = csvParser.parseCsv(inputStream);
        return parse(csv, parseMode);
    }


    /**
     * Parses a RADx data dictionary from the provided Csv object.
     *
     * @param csv The Csv object containing the RADx data dictionary information. Must not be null.
     * @param parseMode Specifies the parse mode.  With {@link ParseMode#STRICT} any validation
     *                  errors will cause a {@link RADxDataDictionaryParseException} to be thrown.  With
     *                  {@link ParseMode#LAX} as much as possible will be parsed without raising errors.  Any
     *                  validation errors will be ignored.
     * @return A RADxDataDictionary object representing the parsed data dictionary.
     * @throws RADxDataDictionaryParseException   If an error occurs during parsing the RADx data dictionary from the Csv data.  The data dictionary is validated and an error is raised if validation fails.
     * @throws NullPointerException                 If the provided Csv object is null.
     */
    @Nonnull
    public RADxDataDictionary parse(@Nonnull Csv csv, ParseMode parseMode) throws RADxDataDictionaryParseException {
        Objects.requireNonNull(csv);
        if (parseMode.equals(ParseMode.STRICT)) {
            validate(csv);
        }
        var elements = csv.dataRows()
                .stream()
                .map(csvRow -> toDadaDictionaryRecord(csv, csvRow))
                .toList();
        return new RADxDataDictionary(elements);

    }

    private void validate(Csv csv) throws RADxDataDictionaryParseException {
        var validationReport = validator.validateDataDictionary(csv);
        var firstError = validationReport.results()
                .stream().filter(r -> r.validationLevel().equals(ValidationLevel.ERROR))
                .findFirst();
        if(firstError.isPresent()) {
            var error = firstError.get();
            throw new RADxDataDictionaryParseException(error);
        }
    }

    private RADxDataDictionaryRecord toDadaDictionaryRecord(Csv csv, CsvRow row) {
        var id = getFieldValue(csv, row, FieldName.ID, "");
        var label = getFieldValue(csv, row, FieldName.LABEL, "");
        var description = getFieldValue(csv, row, FieldName.DESCRIPTION, "");
        var section = getFieldValue(csv, row, FieldName.SECTION, "");
        var cardinality = getFieldValue(csv, row, FieldName.CARDINALITY, "single");
        var datatype = getFieldValue(csv, row, FieldName.DATATYPE, "");
        var pattern = getFieldValue(csv, row, FieldName.PATTERN, "");
        var unit = getFieldValue(csv, row, FieldName.UNIT, "");
        var enumeration = getFieldValue(csv, row, FieldName.ENUMERATION, "");
        var missingValueCodes = getFieldValue(csv, row, FieldName.MISSING_VALUE_CODES, "");
        var nodes = getFieldValue(csv, row, FieldName.NOTES, "");
        var provenance = getFieldValue(csv, row, FieldName.PROVENANCE, "");
        var seeAlso = getFieldValue(csv, row, FieldName.SEE_ALSO, "");
        var termsFieldValue = getFieldValue(csv, row, FieldName.TERMS, "");
        var terms = Stream.of(termsFieldValue.split("\\s+"))
                .filter(t -> !t.isBlank())
                          .map(TermIdentifier::new)
                .toList();
        return new RADxDataDictionaryRecord(
                id,
                label,
                description,
                section,
                terms,
                Cardinality.of(cardinality),
                parseDatatype(datatype),
                parsePattern(pattern).orElse(null),
                parseUnit(unit).orElse(null),
                parseEnumeration(enumeration).orElse(null),
                parseMissingValueCodes(missingValueCodes).orElse(null),
                nodes,
                provenance,
                parseSeeAlso(seeAlso).orElse(null)
        );

    }

    private Optional<URI> parseSeeAlso(String seeAlso) {
        if(seeAlso.isEmpty()) {
            return Optional.empty();
        }
        else {
            try {
                return Optional.of(new URI(seeAlso));
            } catch (URISyntaxException e) {
                return Optional.empty();
            }
        }
    }

    private Optional<Enumeration> parseMissingValueCodes(String missingValueCodes) {
        return parseEnumeration(missingValueCodes);
    }

    private Optional<Enumeration> parseEnumeration(String enumeration) {
        if(enumeration.isEmpty()) {
            return Optional.empty();
        }
        else {
            try {
                return Optional.of(new EnumerationParser(new StringReader(enumeration)).Enumeration());
            } catch (ParseException e) {
                return Optional.empty();
            }
        }
    }

    @Nonnull
    private Datatype parseDatatype(String datatype) {
        if(datatype.isEmpty()) {
            return new Datatype("string");
        }
        else {
            return new Datatype(datatype);
        }
    }

    @Nonnull
    private Optional<Pattern> parsePattern(String pattern) {
        if(pattern.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(Pattern.compile(pattern));
        } catch (PatternSyntaxException e) {
            return Optional.empty();
        }
    }

    @Nonnull
    private Optional<Unit> parseUnit(String unit) {
        if(unit.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Unit(unit));
    }


    private String getFieldValue(Csv csv, CsvRow row, FieldName fieldName, String defaultValue) {
        var index = csv.getIndex(fieldName);
        if(index < 0) {
            return defaultValue;
        }
        var rowData = row.rowData();
        if(index >= rowData.size()) {
            return defaultValue;
        }
        return rowData.get(index);

    }
}
