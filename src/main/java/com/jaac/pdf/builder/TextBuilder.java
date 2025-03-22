package com.jaac.pdf.builder;

import com.jaac.pdf.element.TextElement;
import com.jaac.pdf.loader.FontLoader;
import com.jaac.pdf.main.DonPdf;
import com.jaac.pdf.parser.ColorParser;
import com.jaac.pdf.property.TextProperty;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.IOException;

public class TextBuilder {
    private final DonPdf parent;
    private String content;
    private PdfFont font;
    private Float fontSize;
    private Color color;
    private TextAlignment alignment;
    private Border border;
    private float marginTop;
    private float marginRight;
    private float marginBottom;
    private float marginLeft;

    public TextBuilder(DonPdf parent) {
        this.parent = parent;
    }

    public TextBuilder content(String text) {
        this.content = text;
        return this;
    }

    public TextBuilder font(PdfFont font) {
        this.font = font;
        return this;
    }

    public TextBuilder font(String fontPath) {
        try {
            FontLoader loader = new FontLoader(fontPath);
            this.font = loader.loadPdfFont();
        } catch (IOException e) {
            throw new RuntimeException("Error loading font: " + fontPath, e);
        }
        return this;
    }

    public TextBuilder fontSize(float size) {
        this.fontSize = size;
        return this;
    }

    public TextBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public TextBuilder color(String color) {
        this.color = ColorParser.parse(color);
        return this;
    }

    public TextBuilder alignment(TextAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public TextBuilder alignmentLeft() {
        this.alignment = TextAlignment.LEFT;
        return this;
    }

    public TextBuilder alignmentCenter() {
        this.alignment = TextAlignment.CENTER;
        return this;
    }

    public TextBuilder alignmentRight() {
        this.alignment = TextAlignment.RIGHT;
        return this;
    }

    public TextBuilder alignmentJustified() {
        this.alignment = TextAlignment.JUSTIFIED;
        return this;
    }

    public TextBuilder alignmentJustifiedAll() {
        this.alignment = TextAlignment.JUSTIFIED_ALL;
        return this;
    }

    public TextBuilder border(Border border) {
        this.border = border;
        return this;
    }

    public TextBuilder margins(float top, float right, float bottom, float left) {
        this.marginTop = top;
        this.marginRight = right;
        this.marginBottom = bottom;
        this.marginLeft = left;
        return this;
    }

    public DonPdf next() {
        TextProperty properties = TextProperty
                .builder()
                .font(font)
                .fontSize(fontSize)
                .color(color)
                .alignment(alignment)
                .border(border)
                .margins(marginTop, marginRight, marginBottom, marginLeft)
                .build();
        parent.getElements().add(new TextElement(content, properties));
        return parent;
    }
}
