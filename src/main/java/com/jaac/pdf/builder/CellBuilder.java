package com.jaac.pdf.builder;

import com.jaac.pdf.loader.FontLoader;
import com.jaac.pdf.parser.ColorParser;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.IOException;

public class CellBuilder {
    private final RowBuilder parent;
    private String content;
    private PdfFont font;
    private Float fontSize;
    private Color color;
    private Color backgroundColor;
    private TextAlignment alignment;
    private Border border;

    public CellBuilder(RowBuilder parent) {
        this.parent = parent;
    }

    public CellBuilder content(String content) {
        this.content = content;
        return this;
    }

    public CellBuilder font(String fontPath) {
        try {
            FontLoader loader = new FontLoader(fontPath);
            this.font = loader.loadPdfFont();
        } catch (IOException e) {
            throw new RuntimeException("Error loading font: " + fontPath, e);
        }
        return this;
    }

    public CellBuilder font(PdfFont font) {
        this.font = font;
        return this;
    }

    public CellBuilder fontSize(float size) {
        this.fontSize = size;
        return this;
    }

    public CellBuilder color(String color) {
        this.color = ColorParser.parse(color);
        return this;
    }

    public CellBuilder color(int r, int g, int b) {
        validateRGBValue(r, "Red");
        validateRGBValue(g, "Green");
        validateRGBValue(b, "Blue");
        this.color = new DeviceRgb(r, g, b);
        return this;
    }

    public CellBuilder background(String color) {
        this.backgroundColor = ColorParser.parse(color);
        return this;
    }

    public CellBuilder background(int r, int g, int b) {
        validateRGBValue(r, "Red");
        validateRGBValue(g, "Green");
        validateRGBValue(b, "Blue");
        this.backgroundColor = new DeviceRgb(r, g, b);
        return this;
    }

    public CellBuilder alignment(TextAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public CellBuilder alignmentLeft() {
        this.alignment = TextAlignment.LEFT;
        return this;
    }

    public CellBuilder alignmentCenter() {
        this.alignment = TextAlignment.CENTER;
        return this;
    }

    public CellBuilder alignmentRight() {
        this.alignment = TextAlignment.RIGHT;
        return this;
    }

    public CellBuilder alignmentJustified() {
        this.alignment = TextAlignment.JUSTIFIED;
        return this;
    }

    public CellBuilder alignmentJustifiedAll() {
        this.alignment = TextAlignment.JUSTIFIED_ALL;
        return this;
    }

    public CellBuilder border(Border border) {
        this.border = border;
        return this;
    }

    public CellBuilder addCell() {
        return parent.addCell();
    }

    public TableBuilder endRow() {
        return parent.endRow();
    }

    public Cell build() {
        Paragraph paragraph = new Paragraph(content);
        if (font != null) paragraph.setFont(font);
        if (fontSize != null) paragraph.setFontSize(fontSize);
        if (color != null) paragraph.setFontColor(color);
        Cell cell = new Cell().add(paragraph);
        if (backgroundColor != null) cell.setBackgroundColor(backgroundColor);
        if (alignment != null) cell.setTextAlignment(alignment);
        if (border != null) cell.setBorder(border);
        return cell;
    }

    private void validateRGBValue(int value, String colorName) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException(String.format("%s value must be between 0 and 255, got: %d", colorName, value));
        }
    }
}
