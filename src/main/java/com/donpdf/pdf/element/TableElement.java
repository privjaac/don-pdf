package com.donpdf.pdf.element;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.donpdf.pdf.property.TableProperty;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

public class TableElement implements Element {
    private final Table table;
    private final TableProperty property;

    public TableElement(Table table, TableProperty property) {
        this.table = table;
        this.property = property;
    }

    @Override
    public void addToDocument(Document document, PdfFont defaultFont, Float defaultFontSize) {
        ofNullable(defaultFont)
                .or(() -> ofNullable(property.getFont()))
                .ifPresent(table::setFont);
        ofNullable(defaultFontSize)
                .filter(size -> size > 0)
                .or(() -> ofNullable(property.getFontSize()).filter(size -> size > 0))
                .ifPresent(table::setFontSize);
        of(property.getWidth()).filter(width -> width > 0).ifPresent(table::setWidth);
        ofNullable(property.getAlignment()).ifPresent(table::setHorizontalAlignment);
        ofNullable(property.getBorder()).ifPresent(table::setBorder);
        ofNullable(property.getMarginTop()).ifPresent(table::setMarginTop);
        ofNullable(property.getMarginBottom()).ifPresent(table::setMarginBottom);
        ofNullable(property.getMarginRight()).ifPresent(table::setMarginRight);
        ofNullable(property.getMarginLeft()).ifPresent(table::setMarginLeft);
        ofNullable(property.getPaddingTop()).ifPresent(table::setPaddingTop);
        ofNullable(property.getPaddingBottom()).ifPresent(table::setPaddingBottom);
        ofNullable(property.getPaddingRight()).ifPresent(table::setPaddingRight);
        ofNullable(property.getPaddingLeft()).ifPresent(table::setPaddingLeft);
        ofNullable(property.getBorderRadius()).ifPresent(table::setBorderRadius);
        ofNullable(property.getBackgroundColor()).ifPresent(table::setBackgroundColor);
        document.add(table);
    }
}
