package com.jaac.pdf.element;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.jaac.pdf.fragment.TextFragment;
import com.jaac.pdf.property.TextProperty;

import java.util.List;

import static java.util.Optional.ofNullable;

public class TextElement implements Element {
    private final TextProperty paragraphProperties;
    private final List<TextFragment> fragments;

    public TextElement(List<TextFragment> fragments, TextProperty paragraphProperties) {
        this.fragments = fragments;
        this.paragraphProperties = paragraphProperties;
    }

    @Override
    public void addToDocument(Document document, PdfFont defaultFont, Float defaultFontSize) {
        try {
            Paragraph paragraph = new Paragraph();
            applyParagraphProperties(paragraph, defaultFont, defaultFontSize);
            for (TextFragment fragment : fragments) {
                Text textElement = new Text(fragment.getText());
                applyFragmentProperties(textElement, fragment.getProperties(), defaultFont, defaultFontSize);
                paragraph.add(textElement);
            }
            document.add(paragraph);
        } catch (Exception e) {
            throw new RuntimeException("Error al añadir texto al documento: " + e.getMessage(), e);
        }
    }

    private void applyParagraphProperties(Paragraph paragraph, PdfFont defaultFont, Float defaultFontSize) {
        ofNullable(paragraphProperties.getAlignment()).ifPresent(paragraph::setTextAlignment);
        ofNullable(paragraphProperties.getMarginTop()).ifPresent(paragraph::setMarginTop);
        ofNullable(paragraphProperties.getMarginBottom()).ifPresent(paragraph::setMarginBottom);
        ofNullable(paragraphProperties.getMarginRight()).ifPresent(paragraph::setMarginRight);
        ofNullable(paragraphProperties.getMarginLeft()).ifPresent(paragraph::setMarginLeft);
        ofNullable(paragraphProperties.getBorder()).ifPresent(paragraph::setBorder);
    }

    private void applyFragmentProperties(Text textElement, TextProperty properties, PdfFont defaultFont, Float defaultFontSize) {
        PdfFont font = ofNullable(properties.getFont()).orElse(defaultFont);
        if (font != null) textElement.setFont(font);
        Float fontSize = ofNullable(properties.getFontSize()).filter(size -> size > 0).orElse(defaultFontSize);
        if (fontSize != null && fontSize > 0) textElement.setFontSize(fontSize);
        if (properties.getColor() != null) textElement.setFontColor(properties.getColor());
        if (ofNullable(properties.getIsBold()).orElse(false)) textElement.setBold();
        if (ofNullable(properties.getIsItalic()).orElse(false)) textElement.setItalic();
        if (ofNullable(properties.getIsUnderlined()).orElse(false)) textElement.setUnderline();
        if (properties.getWordSpacing() != null) textElement.setWordSpacing(properties.getWordSpacing());
        if (properties.getHyperlinkUrl() != null) textElement.setAction(PdfAction.createURI(properties.getHyperlinkUrl()));
    }
}
