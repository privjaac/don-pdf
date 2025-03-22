package com.jaac.pdf.loader;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;

import java.io.IOException;

public class FontLoader extends ResourceLoader {
    public FontLoader(String path) {
        super(path);
    }

    public PdfFont loadPdfFont() throws IOException {
        return loadPdfFont(PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
    }

    public PdfFont loadPdfFont(String encoding, PdfFontFactory.EmbeddingStrategy embeddingStrategy) throws IOException {
        byte[] fontData = loadResource();
        return PdfFontFactory.createFont(fontData, encoding, embeddingStrategy);
    }
}
