package edu.stanford.bmir.radx.datadictionary.lib;

import jakarta.annotation.Nonnull;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 */
public class CsvParser {

    @Nonnull
    public Csv parseCsv(@Nonnull InputStream inputStream) throws IOException {
        var records = parseRecords(inputStream);
        var rows = translateRecordsToRows(records);
        var header = records.get(0).toList();
        return new Csv(new CsvRow(0, header), rows);
    }

    private static ArrayList<CsvRow> translateRecordsToRows(List<CSVRecord> records) {
        var rows = new ArrayList<CsvRow>();
        for(int i = 1; i < records.size(); i++) {
            var data = records.get(i).toList();
            var row = new CsvRow(i, data);
            rows.add(row);
        }
        return rows;
    }

    private static List<CSVRecord> parseRecords(InputStream inputStream) throws IOException {
        try (var parser = new CSVParser(new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)), CSVFormat.DEFAULT)) {
            return parser.getRecords();
        }
    }
}
