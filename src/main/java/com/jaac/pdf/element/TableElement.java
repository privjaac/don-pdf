package com.jaac.pdf.element;

import com.jaac.pdf.property.TableProperty;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

import java.util.Optional;

public class TableElement implements Element {
    private final Table table;
    private final TableProperty property;

    public TableElement(Table table, TableProperty property) {
        this.table = table;
        this.property = property;
    }

    @Override
    public void addToDocument(Document document, PdfFont defaultFont, Float defaultFontSize) {
        if (defaultFont != null) table.setFont(Optional.ofNullable(property.getFont()).orElse(defaultFont));
        if (defaultFontSize != null) table.setFontSize(Optional.ofNullable(property.getFontSize()).filter(size -> size > 0).orElse(defaultFontSize));
        if (property.getWidth() > 0) table.setWidth(property.getWidth());
        if (property.getAlignment() != null) table.setHorizontalAlignment(property.getAlignment());
        if (property.getBorder() != null) table.setBorder(property.getBorder());
        if (property.getMarginTop() != null) table.setMarginTop(property.getMarginTop());
        if (property.getMarginBottom() != null) table.setMarginBottom(property.getMarginBottom());
        if (property.getMarginRight() != null) table.setMarginRight(property.getMarginRight());
        if (property.getMarginLeft() != null) table.setMarginLeft(property.getMarginLeft());
        document.add(table);
    }
}
