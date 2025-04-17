package com.donpdf.pdf.builder;

import com.donpdf.pdf.loader.FontLoader;
import com.donpdf.pdf.parser.ColorParser;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.BorderRadius;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.TextAlignment;

public class CellBuilder {
    private final RowBuilder parent;
    private String content;
    private PdfFont font;
    private Float fontSize;
    private Color color;
    private Boolean bold;
    private Boolean italic;
    private Boolean underline;
    private Color backgroundColor;
    private TextAlignment alignment;
    private Border border;
    private BorderRadius borderRadius;
    private Float paddingTop;
    private Float paddingRight;
    private Float paddingBottom;
    private Float paddingLeft;
    private Float wordSpacing;
    private Integer colspan = 1;
    private Integer rowspan = 1;

    public CellBuilder(RowBuilder parent) {
        this.parent = parent;
    }

    public CellBuilder content(String content) {
        this.content = content;
        return this;
    }

    public CellBuilder font(String fontPath) {
        FontLoader loader = new FontLoader(fontPath);
        this.font = loader.loadPdfFont();
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

    public CellBuilder color(Color color) {
        this.color = color;
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

    public CellBuilder background(Color color) {
        this.backgroundColor = color;
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

    public CellBuilder borderRadius(BorderRadius borderRadius) {
        this.borderRadius = borderRadius;
        return this;
    }

    public CellBuilder bold() {
        this.bold = true;
        return this;
    }

    public CellBuilder bold(Boolean isBold) {
        this.bold = isBold;
        return this;
    }

    public CellBuilder italic() {
        this.italic = true;
        return this;
    }

    public CellBuilder underline() {
        this.underline = true;
        return this;
    }

    public CellBuilder wordSpacing(Float wordSpacing) {
        this.wordSpacing = wordSpacing;
        return this;
    }

    public CellBuilder paddingTop(Float paddingTop) {
        this.paddingTop = paddingTop;
        return this;
    }

    public CellBuilder paddingRight(Float paddingRight) {
        this.paddingRight = paddingRight;
        return this;
    }

    public CellBuilder paddingBottom(Float paddingBottom) {
        this.paddingBottom = paddingBottom;
        return this;
    }

    public CellBuilder paddingLeft(Float paddingLeft) {
        this.paddingLeft = paddingLeft;
        return this;
    }

    public CellBuilder colspan(Integer colspan) {
        this.colspan = colspan;
        return this;
    }

    public CellBuilder rowspan(Integer rowspan) {
        this.rowspan = rowspan;
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
        Cell cell = new Cell(rowspan, colspan);
        cell.add(paragraph);
        if (backgroundColor != null) cell.setBackgroundColor(backgroundColor);
        if (alignment != null) cell.setTextAlignment(alignment);
        if (border != null) cell.setBorder(border);
        if (borderRadius != null) cell.setBorderRadius(borderRadius);
        if (bold != null && bold) cell.setProperty(Property.BOLD_SIMULATION, true);
        if (italic != null && italic) cell.setProperty(Property.ITALIC_SIMULATION, true);
        if (underline != null && underline) cell.setUnderline();
        if (paddingTop != null) cell.setPaddingTop(paddingTop);
        if (paddingRight != null) cell.setPaddingRight(paddingRight);
        if (paddingBottom != null) cell.setPaddingBottom(paddingBottom);
        if (paddingLeft != null) cell.setPaddingLeft(paddingLeft);
        if (wordSpacing != null) cell.setWordSpacing(wordSpacing);
        return cell;
    }

    private void validateRGBValue(int value, String colorName) {
        if (value < 0 || value > 255) throw new IllegalArgumentException(String.format("%s value must be between 0 and 255, got: %d", colorName, value));
    }
}
