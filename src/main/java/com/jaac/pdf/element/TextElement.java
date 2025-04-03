package com.jaac.pdf.element;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.jaac.pdf.property.TextProperty;

import static java.util.Optional.ofNullable;

public class TextElement implements Element {
    private final String content;
    private final TextProperty property;
    private final Paragraph inlineParagraph;

    public TextElement(String text, TextProperty property) {
        this.content = text;
        this.property = property;
        this.inlineParagraph = null;
    }

    public TextElement(Paragraph paragraph, TextProperty property) {
        this.content = null;
        this.property = property;
        this.inlineParagraph = paragraph;
    }

    @Override
    public void addToDocument(Document document, PdfFont defaultFont, Float defaultFontSize) {
        try {
            Paragraph paragraph;
            if (inlineParagraph != null) paragraph = inlineParagraph;
            else {
                paragraph = new Paragraph(content);
                ofNullable(defaultFont)
                        .or(() -> ofNullable(property.getFont()))
                        .ifPresent(paragraph::setFont);
                ofNullable(defaultFontSize)
                        .filter(size -> size > 0)
                        .or(() -> ofNullable(property.getFontSize()).filter(size -> size > 0))
                        .ifPresent(paragraph::setFontSize);
                ofNullable(property.getColor()).ifPresent(paragraph::setFontColor);
                ofNullable(property.getAlignment()).ifPresent(paragraph::setTextAlignment);
            }
            ofNullable(property.getBorder()).ifPresent(paragraph::setBorder);
            ofNullable(property.getMarginTop()).ifPresent(paragraph::setMarginTop);
            ofNullable(property.getMarginBottom()).ifPresent(paragraph::setMarginBottom);
            ofNullable(property.getMarginRight()).ifPresent(paragraph::setMarginRight);
            ofNullable(property.getMarginLeft()).ifPresent(paragraph::setMarginLeft);
            document.add(paragraph);
        } catch (Exception e) {
            throw new RuntimeException("Error al añadir texto al documento", e);
        }
    }
}
