package com.jaac.pdf.element;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.action.PdfAction;
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
            else paragraph = new Paragraph(content);
            ofNullable(defaultFont)
                    .or(() -> ofNullable(property.getFont()))
                    .ifPresent(paragraph::setFont);
            ofNullable(defaultFontSize)
                    .filter(size -> size > 0)
                    .or(() -> ofNullable(property.getFontSize()).filter(size -> size > 0))
                    .ifPresent(paragraph::setFontSize);
            // ofNullable(property.getColor()).ifPresent(paragraph::setFontColor);
            ofNullable(property.getAlignment()).ifPresent(paragraph::setTextAlignment);

            // ofNullable(property.getMarginTop()).ifPresent(paragraph::setMarginTop);
            // ofNullable(property.getMarginBottom()).ifPresent(paragraph::setMarginBottom);
            // ofNullable(property.getMarginRight()).ifPresent(paragraph::setMarginRight);
            // ofNullable(property.getMarginLeft()).ifPresent(paragraph::setMarginLeft);
            // ofNullable(property.getBorder()).ifPresent(paragraph::setBorder);
            // ofNullable(property.getIsBold()).filter(flag -> flag).ifPresent(flag -> paragraph.setBold());
            // ofNullable(property.getIsItalic()).filter(flag -> flag).ifPresent(flag -> paragraph.setItalic());
            // ofNullable(property.getIsUnderlined()).filter(flag -> flag).ifPresent(flag -> paragraph.setUnderline());
            // ofNullable(property.getWordSpacing()).ifPresent(paragraph::setWordSpacing);
            // ofNullable(property.getHyperlinkUrl()).ifPresent(url -> paragraph.setAction(PdfAction.createURI(url)));
            document.add(paragraph);
        } catch (Exception e) {
            throw new RuntimeException("Error al añadir texto al documento", e);
        }
    }
}
