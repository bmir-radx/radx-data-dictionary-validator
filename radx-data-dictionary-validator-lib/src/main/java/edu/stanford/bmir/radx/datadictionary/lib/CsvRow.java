package edu.stanford.bmir.radx.datadictionary.lib;

import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Objects;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2023-08-21
 *
 * Represents a row in a CSV file.  Rows are zero-based indexed.  The header row
 * being index 0.
 *
 * @param rowIndex The zero-based row index of this row.  An illegal arument exception is
 *                 thrown if the row index is less than zero.
 * @param rowData The data for this row.  The list of data does not contain null values.
 */
public record CsvRow(int rowIndex, List<String> rowData) {

    public CsvRow(int rowIndex, List<String> rowData) {
        this.rowIndex = rowIndex;
        if(rowIndex < 0) {
            throw new IllegalArgumentException("rowIndex < 0");
        }
        this.rowData = rowData.stream()
                .peek(Objects::requireNonNull)
                .toList();
    }

    public int size() {
        return rowData.size();
    }

    @Nonnull
    public String get(int index) {
        return rowData.get(index);
    }

    public int indexOf(String value) {
        return rowData.indexOf(value);
    }
}
