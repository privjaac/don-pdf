package com.jaac.pdf.builder;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.jaac.pdf.element.TextElement;
import com.jaac.pdf.loader.FontLoader;
import com.jaac.pdf.main.DonPdf;
import com.jaac.pdf.parser.ColorParser;
import com.jaac.pdf.property.TextProperty;

import java.io.IOException;

public class TextBuilder {
    private final DonPdf parent;
    private String content;
    private PdfFont font;
    private Float fontSize;
    private Color color;
    private TextAlignment alignment;
    private Border border;
    private Float marginTop;
    private Float marginRight;
    private Float marginBottom;
    private Float marginLeft;
    private Boolean bold;
    private Boolean italic;
    private Boolean underline;
    private Float wordSpacing;
    private String hyperlinkUrl;
    private Boolean inlineMode;
    private Paragraph inlineParagraph;
    private Text lastTextElement;


    public TextBuilder(DonPdf parent) {
        this(parent, false);
    }

    public TextBuilder(DonPdf parent, Boolean inlineMode) {
        this.parent = parent;
        this.inlineMode = inlineMode;
        if (inlineMode) {
            this.inlineParagraph = new Paragraph();
            this.lastTextElement = null;
        }
    }

    public TextBuilder newline() {
        this.inlineMode = true;
        this.inlineParagraph = new Paragraph();
        this.lastTextElement = null;
        return this;
    }

    public TextBuilder content(String text) {
        if (inlineMode) {
            Text textElement = new Text(text);
            this.lastTextElement = textElement;
            inlineParagraph.add(textElement);
        } else this.content = text;
        return this;
    }

    public TextBuilder font(PdfFont font) {
        this.font = font;
        if (inlineMode && lastTextElement != null) lastTextElement.setFont(font);
        return this;
    }

    public TextBuilder font(String fontPath) {
        try {
            FontLoader loader = new FontLoader(fontPath);
            this.font = loader.loadPdfFont();
            if (inlineMode && lastTextElement != null) lastTextElement.setFont(this.font);
        } catch (IOException e) {
            throw new RuntimeException("Error loading font: " + fontPath, e);
        }
        return this;
    }

    public TextBuilder fontSize(float size) {
        this.fontSize = size;
        if (inlineMode && lastTextElement != null) lastTextElement.setFontSize(size);
        return this;
    }

    public TextBuilder color(Color color) {
        this.color = color;
        if (inlineMode && lastTextElement != null) lastTextElement.setFontColor(color);
        return this;
    }

    public TextBuilder color(String color) {
        this.color = ColorParser.parse(color);
        if (inlineMode && lastTextElement != null) lastTextElement.setFontColor(this.color);
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

    public TextBuilder bold() {
        this.bold = true;
        if (inlineMode && lastTextElement != null) lastTextElement.setBold();
        return this;
    }

    public TextBuilder bold(Boolean isBold) {
        this.bold = isBold;
        if (this.bold && inlineMode && lastTextElement != null) lastTextElement.setBold();
        return this;
    }

    public TextBuilder italic() {
        this.italic = true;
        if (inlineMode && lastTextElement != null) lastTextElement.setItalic();
        return this;
    }

    public TextBuilder underline() {
        this.underline = true;
        if (inlineMode && lastTextElement != null) lastTextElement.setUnderline();
        return this;
    }

    public TextBuilder wordSpacing(float wordSpacing) {
        this.wordSpacing = wordSpacing;
        if (inlineMode && lastTextElement != null) lastTextElement.setWordSpacing(wordSpacing);
        return this;
    }

    public TextBuilder hyperlink(String url) {
        this.hyperlinkUrl = url;
        if (inlineMode && lastTextElement != null) {
            PdfAction linkAction = PdfAction.createURI(url);
            lastTextElement.setAction(linkAction);
        }
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
                .isBold(bold)
                .isItalic(italic)
                .isUnderline(underline)
                .wordSpacing(wordSpacing)
                .hyperlinkUrl(hyperlinkUrl)
                .build();
        if (inlineMode) parent.getElements().add(new TextElement(inlineParagraph, properties));
        else parent.getElements().add(new TextElement(content, properties));
        this.bold = null;
        this.italic = null;
        this.underline = null;
        this.wordSpacing = null;
        this.hyperlinkUrl = null;
        return parent;
    }
}
