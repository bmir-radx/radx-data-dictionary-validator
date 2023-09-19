package edu.stanford.bmir.radx.datadictionary.lib;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CsvRowTest {

    @Test
    void testValidCsvRowCreation() {
        var rowData = List.of("value1", "value2", "value3");
        var csvRow = new CsvRow(0, rowData);
        assertThat(csvRow.rowIndex()).isEqualTo(0);
        assertThat(csvRow.rowData()).containsExactlyElementsOf(rowData);
    }

    @Test
    void testNegativeRowIndex() {
        var rowData = List.of("value1", "value2");

        assertThatThrownBy(() -> new CsvRow(-1, rowData))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rowIndex < 0");
    }

    @Test
    void testNullRowData() {
        assertThatThrownBy(() -> new CsvRow(0, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testRowDataWithNullValue() {
        var rowData = Arrays.asList("value1", null, "value3");
        assertThatThrownBy(() -> new CsvRow(0, rowData))
                .isInstanceOf(NullPointerException.class);
    }
}
