package com.donpdf.pdf.loader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class HtmlLoader extends ResourceLoader {
    public HtmlLoader(String path) {
        super(path);
    }

    public InputStream loadHtml() {
        try {
            byte[] fontData = loadResource();
            return fontData.length > 0 ? new ByteArrayInputStream(fontData) : null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load HTML: " + resourcePath, e);
        }
    }
}
