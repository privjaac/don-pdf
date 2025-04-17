package com.donpdf.pdf.element;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;

public interface Element {
    void addToDocument(Document document, PdfFont defaultFont, Float defaultFontSize);
}
