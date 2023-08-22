package org.metadatacenter.radx.datadictionary;

import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Objects;

public record Csv(CsvRow headerRow, List<CsvRow> dataRows) {

    public Csv(@Nonnull CsvRow headerRow, @Nonnull List<CsvRow> dataRows) {
        this.headerRow = Objects.requireNonNull(headerRow);
        this.dataRows = Objects.requireNonNull(dataRows);
        if(headerRow.rowIndex() != 0) {
            throw new IllegalArgumentException("Header row index must be 0");
        }
        if(!dataRows.isEmpty()) {
            var firstRow = dataRows.get(0);
            if(firstRow.rowIndex() != 1) {
                throw new IllegalArgumentException("First row index of data rows must be 1");
            }
        }
    }

    public int getIndex(String headerName) {
        return headerRow.indexOf(headerName);
    }

    public int getIndex(FieldName fieldName) {
        return getIndex(fieldName.headerValue());
    }

}
