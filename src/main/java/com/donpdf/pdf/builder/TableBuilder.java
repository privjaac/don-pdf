package com.donpdf.pdf.builder;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.layout.properties.BorderRadius;
import com.donpdf.pdf.element.TableElement;
import com.donpdf.pdf.main.DonPdf;
import com.donpdf.pdf.property.TableProperty;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;

public class TableBuilder {
    private final DonPdf parent;
    private final Table table;
    private float width;
    private HorizontalAlignment alignment;
    private Color backgroundColor;
    private Border border;
    private BorderRadius borderRadius;
    private float marginTop;
    private float marginRight;
    private float marginBottom;
    private float marginLeft;
    private float paddingTop;
    private float paddingRight;
    private float paddingBottom;
    private float paddingLeft;
    private RowBuilder currentRow;

    public TableBuilder(DonPdf parent, float... columnWidths) {
        this.parent = parent;
        this.table = new Table(columnWidths);
        this.currentRow = new RowBuilder(this);
    }

    public TableBuilder width(float width) {
        this.width = width;
        return this;
    }

    public TableBuilder alignment(HorizontalAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public TableBuilder border(Border border) {
        this.border = border;
        return this;
    }

    public TableBuilder margins(float top, float right, float bottom, float left) {
        this.marginTop = top;
        this.marginRight = right;
        this.marginBottom = bottom;
        this.marginLeft = left;
        return this;
    }

    public TableBuilder paddings(float top, float right, float bottom, float left) {
        this.paddingTop = top;
        this.paddingRight = right;
        this.paddingBottom = bottom;
        this.paddingLeft = left;
        return this;
    }

    public TableBuilder borderRadius(BorderRadius borderRadius) {
        this.borderRadius = borderRadius;
        return this;
    }

    public TableBuilder backgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public RowBuilder addRow() {
        if (currentRow != null && !currentRow.isEmpty()) currentRow.addToTable();
        currentRow = new RowBuilder(this);
        return currentRow;
    }

    public DonPdf next() {
        if (currentRow != null && !currentRow.isEmpty()) currentRow.addToTable();
        TableProperty properties = TableProperty.builder()
                .width(width)
                .alignment(alignment)
                .margins(marginTop, marginRight, marginBottom, marginLeft)
                .paddings(paddingTop, paddingRight, paddingBottom, paddingLeft)
                .border(border)
                .borderRadius(borderRadius)
                .backgroundColor(backgroundColor)
                .build();
        parent.getElements().add(new TableElement(table, properties));
        return parent;
    }

    public Table getTable() {
        return table;
    }
}
