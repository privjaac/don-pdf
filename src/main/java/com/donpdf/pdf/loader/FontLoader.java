package com.donpdf.pdf.loader;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;

import java.io.IOException;

public class FontLoader extends ResourceLoader {
    public FontLoader(String path) {
        super(path);
    }

    public PdfFont loadPdfFont() {
        try {
            return loadPdfFont(PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load font: " + resourcePath, e);
        }
    }

    public PdfFont loadPdfFont(String encoding, PdfFontFactory.EmbeddingStrategy embeddingStrategy) {
        try {
            byte[] fontData = loadResource();
            return PdfFontFactory.createFont(fontData, encoding, embeddingStrategy);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load font: " + resourcePath, e);
        }
    }
}
