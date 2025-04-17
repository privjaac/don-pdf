package com.donpdf.pdf.builder;

import com.donpdf.pdf.loader.HtmlLoader;
import com.donpdf.pdf.main.DonPdf;
import com.itextpdf.html2pdf.HtmlConverter;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.regex.Matcher;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HtmlBuilder {
    private final DonPdf parent;
    private final OutputStream outputStream;
    private InputStream inputStream;

    public HtmlBuilder(DonPdf parent, String output) {
        this.parent = parent;
        try {
            this.outputStream = new FileOutputStream(output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HtmlBuilder path(String input) {
        HtmlLoader loader = new HtmlLoader(input);
        this.inputStream = loader.loadHtml();
        return this;
    }

    public HtmlBuilder template(Map<String, String> template) {
        try {
            String html = new String(this.inputStream.readAllBytes(), UTF_8);
            for (Map.Entry<String, String> entry : template.entrySet()) {
                String placeholder = "\\{\\{\\s*" + entry.getKey() + "\\s*}}";
                String value = (entry.getValue() != null) ? entry.getValue() : "";
                html = html.replaceAll(placeholder, Matcher.quoteReplacement(value));
            }
            this.inputStream = new ByteArrayInputStream(html.getBytes(UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read html template: " + e.getMessage(), e);
        }
        return this;
    }

    public DonPdf build() {
        try {
            HtmlConverter.convertToPdf(this.inputStream, this.outputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert html to pdf: " + e.getMessage(), e);
        }
        return parent;
    }
}
