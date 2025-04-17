package com.donpdf.pdf.builder;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.properties.TextAlignment;
import com.donpdf.pdf.fragment.TextFragment;
import com.donpdf.pdf.element.TextElement;
import com.donpdf.pdf.loader.FontLoader;
import com.donpdf.pdf.main.DonPdf;
import com.donpdf.pdf.parser.ColorParser;
import com.donpdf.pdf.property.TextProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextBuilder {
    private final DonPdf parent;
    private TextAlignment alignment;
    private Border border;
    private Float marginTop;
    private Float marginRight;
    private Float marginBottom;
    private Float marginLeft;
    private List<TextFragment> fragments;
    private PdfFont currentFont;
    private Float currentFontSize;
    private Color currentColor;
    private Boolean currentBold;
    private Boolean currentItalic;
    private Boolean currentUnderline;
    private Float currentWordSpacing;
    private String currentHyperlinkUrl;

    public TextBuilder(DonPdf parent) {
        this.parent = parent;
        this.fragments = new ArrayList<>();
    }

    public TextBuilder content(String text) {
        applyCurrentPropertiesToLastFragment();
        TextFragment fragment = new TextFragment(text);
        fragments.add(fragment);
        return this;
    }

    private void applyCurrentPropertiesToLastFragment() {
        if (!fragments.isEmpty()) {
            TextFragment lastFragment = fragments.getLast();
            TextProperty.Builder builder = TextProperty.builder()
                    .font(currentFont)
                    .fontSize(currentFontSize)
                    .color(currentColor)
                    .isBold(currentBold)
                    .isItalic(currentItalic)
                    .isUnderline(currentUnderline)
                    .wordSpacing(currentWordSpacing)
                    .hyperlinkUrl(currentHyperlinkUrl);
            lastFragment.setProperties(builder.build());
        }
    }

    public TextBuilder font(PdfFont font) {
        this.currentFont = font;
        return this;
    }

    public TextBuilder font(String fontPath) {
        try {
            FontLoader loader = new FontLoader(fontPath);
            this.currentFont = loader.loadPdfFont();
        } catch (IOException e) {
            throw new RuntimeException("Error loading font: " + fontPath, e);
        }
        return this;
    }

    public TextBuilder fontSize(Float size) {
        this.currentFontSize = size;
        return this;
    }

    public TextBuilder color(Color color) {
        this.currentColor = color;
        return this;
    }

    public TextBuilder color(String color) {
        this.currentColor = ColorParser.parse(color);
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
        this.currentBold = true;
        return this;
    }

    public TextBuilder bold(Boolean isBold) {
        this.currentBold = isBold;
        return this;
    }

    public TextBuilder italic() {
        this.currentItalic = true;
        return this;
    }

    public TextBuilder underline() {
        this.currentUnderline = true;
        return this;
    }

    public TextBuilder wordSpacing(float wordSpacing) {
        this.currentWordSpacing = wordSpacing;
        return this;
    }

    public TextBuilder hyperlink(String url) {
        this.currentHyperlinkUrl = url;
        return this;
    }

    public DonPdf next() {
        applyCurrentPropertiesToLastFragment();
        TextProperty paragraphProperties = TextProperty.builder()
                .alignment(alignment)
                .border(border)
                .margins(marginTop, marginRight, marginBottom, marginLeft)
                .build();
        parent.getElements().add(new TextElement(fragments, paragraphProperties));
        resetBuilder();
        return parent;
    }

    private void resetBuilder() {
        this.alignment = null;
        this.border = null;
        this.marginTop = null;
        this.marginRight = null;
        this.marginBottom = null;
        this.marginLeft = null;
        this.currentFont = null;
        this.currentFontSize = null;
        this.currentColor = null;
        this.currentBold = null;
        this.currentItalic = null;
        this.currentUnderline = null;
        this.currentWordSpacing = null;
        this.currentHyperlinkUrl = null;
        this.fragments = new ArrayList<>();
    }
}
