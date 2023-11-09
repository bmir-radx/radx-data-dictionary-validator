package edu.stanford.bmir.radx.datadictionary.lib;

import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Objects;

/**
 * The `Csv` class represents a CSV (Comma-Separated Values) data structure, consisting of a header row
 * and a list of data rows. It provides methods to retrieve the index of a header by name or field name.
 *
 * @param headerRow The {@link CsvRow} representing the header row of the CSV.
 * @param dataRows A list of {@link CsvRow} instances representing the data rows of the CSV.
 */
public record Csv(@Nonnull CsvRow headerRow, @Nonnull List<CsvRow> dataRows) {

    /**
     * Constructs a new `Csv` with the specified header row and data rows.
     *
     * @param headerRow The {@link CsvRow} representing the header row of the CSV.
     * @param dataRows A list of {@link CsvRow} instances representing the data rows of the CSV.
     * @throws IllegalArgumentException If the header row index is not 0 or the first data row index is not 1.
     */
    public Csv(@Nonnull CsvRow headerRow, @Nonnull List<CsvRow> dataRows) {
        this.headerRow = Objects.requireNonNull(headerRow);
        this.dataRows = Objects.requireNonNull(dataRows);
        if (headerRow.rowIndex() != 0) {
            throw new IllegalArgumentException("Header row index must be 0");
        }
        if (!dataRows.isEmpty()) {
            var firstRow = dataRows.get(0);
            if (firstRow.rowIndex() != 1) {
                throw new IllegalArgumentException("First row index of data rows must be 1");
            }
        }
    }

    /**
     * Retrieves the index of a header by its name.
     *
     * @param headerName The name of the header for which the index is requested.
     * @return The index of the specified header in the header row.  Returns -1 if the specified name
     *         is not a header in the CSV.
     */
    public int getIndex(String headerName) {
        return headerRow.indexOf(headerName);
    }

    /**
     * Retrieves the index of a header by its field name.
     *
     * @param fieldName The {@link FieldName} representing the field name of the header.
     * @return The index of the specified header in the header row. Returns -1 if the specified name
     *         is not a header in the CSV.
     */
    public int getIndex(FieldName fieldName) {
        return getIndex(fieldName.headerValue());
    }
}

