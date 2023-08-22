package org.metadatacenter.radx.datadictionary;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CsvTest {

    @Test
    void testValidCsvCreation() {
        var headerRow = new CsvRow(0, List.of("Header1", "Header2", "Header3"));
        var dataRow1 = new CsvRow(1, List.of("Value11", "Value12", "Value13"));
        var dataRow2 = new CsvRow(2, List.of("Value21", "Value22", "Value23"));
        var dataRows = List.of(dataRow1, dataRow2);

        var csv = new Csv(headerRow, dataRows);

        assertThat(csv.headerRow()).isEqualTo(headerRow);
        assertThat(csv.dataRows()).containsExactlyElementsOf(dataRows);
    }

    @Test
    void testInvalidHeaderRowIndex() {
        var headerRow = new CsvRow(1, List.of("Header1", "Header2"));
        var dataRows = List.of(new CsvRow(1, List.of("Value1", "Value2")));

        assertThatThrownBy(() -> new Csv(headerRow, dataRows))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testInvalidFirstDataRowIndex() {
        var headerRow = new CsvRow(0, List.of("Header1", "Header2"));
        var dataRows = List.of(new CsvRow(0, List.of("Value1", "Value2")));

        assertThatThrownBy(() -> new Csv(headerRow, dataRows))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testGetIndex() {
        var headerRow = new CsvRow(0, List.of("Header1", "Header2", "Header3"));
        var csv = new Csv(headerRow, List.of());
        assertThat(csv.getIndex("Header2")).isEqualTo(1);
    }
}
