package com.donpdf.pdf.builder;

import com.itextpdf.layout.element.Cell;

import java.util.ArrayList;
import java.util.List;

public class RowBuilder {
    private final TableBuilder parent;
    private final List<Cell> cells;
    private CellBuilder currentCell;

    public RowBuilder(TableBuilder parent) {
        this.parent = parent;
        this.cells = new ArrayList<>();
    }

    public CellBuilder addCell() {
        // Si hay una celda en construcción, la finalizamos
        if (currentCell != null) cells.add(currentCell.build());
        // Creamos una nueva celda
        currentCell = new CellBuilder(this);
        return currentCell;
    }

    public TableBuilder endRow() {
        // Aseguramos que la última celda se agregue
        if (currentCell != null) {
            cells.add(currentCell.build());
            currentCell = null;
        }
        addToTable();
        return parent;
    }

    public void addToTable() {
        for (Cell cell : cells) parent.getTable().addCell(cell);
        cells.clear();
    }

    public boolean isEmpty() {
        return cells.isEmpty() && currentCell == null;
    }
}
