package com.jaac.pdf.element;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.jaac.pdf.property.TextProperty;

import java.util.Optional;

public class TextElement implements Element {
    private final String text;
    private final TextProperty property;

    public TextElement(String text, TextProperty property) {
        this.text = text;
        this.property = property;
    }

    @Override
    public void addToDocument(Document document, PdfFont defaultFont, Float defaultFontSize) {
        try {
            Paragraph paragraph = new Paragraph(text);
            if (defaultFont != null) paragraph.setFont(Optional.ofNullable(property.getFont()).orElse(defaultFont));
            if (defaultFontSize != null) paragraph.setFontSize(Optional.ofNullable(property.getFontSize()).filter(size -> size > 0).orElse(defaultFontSize));
            if (property.getColor() != null) paragraph.setFontColor(property.getColor());
            if (property.getAlignment() != null) paragraph.setTextAlignment(property.getAlignment());
            if (property.getBorder() != null) paragraph.setBorder(property.getBorder());
            if (property.getMarginTop() != null) paragraph.setMarginTop(property.getMarginTop());
            if (property.getBorder() != null) paragraph.setMarginBottom(property.getMarginBottom());
            if (property.getBorder() != null) paragraph.setMarginRight(property.getMarginRight());
            if (property.getBorder() != null) paragraph.setMarginLeft(property.getMarginLeft());
            document.add(paragraph);
        } catch (Exception e) {
            throw new RuntimeException("Error al añadir texto al documento", e);
        }
    }
}
