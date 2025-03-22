package com.jaac.pdf.main;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.jaac.pdf.builder.ImageBuilder;
import com.jaac.pdf.builder.TableBuilder;
import com.jaac.pdf.builder.TextBuilder;
import com.jaac.pdf.config.PdfConfig;
import com.jaac.pdf.element.Element;
import com.jaac.pdf.loader.FontLoader;
import com.jaac.pdf.parser.ColorParser;
import com.jaac.pdf.utility.BackgroundUtility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DonPdf {
    private static final float DEFAULT_FONT_SIZE = 12f;
    private static final String INITIAL_SUFFIX = "_initial";

    private final PdfConfig config;
    private final List<Element> elements;
    private final List<String> mergePaths;

    private DonPdf() {
        this.config = new PdfConfig();
        this.elements = new ArrayList<>();
        this.mergePaths = new ArrayList<>();
        this.config.setPageSize(PageSize.A4);
        this.config.setDefaultFontSize(DEFAULT_FONT_SIZE);
        this.config.setBackgroundOpacity(1.0f);
    }

    public static DonPdf builder() {
        return new DonPdf();
    }

    public ImageBuilder addImage() {
        return new ImageBuilder(this);
    }

    public TextBuilder addText() {
        return new TextBuilder(this);
    }

    public TableBuilder addTable(float... columnWidths) {
        return new TableBuilder(this, columnWidths);
    }

    public List<Element> getElements() {
        return this.elements;
    }

    public DonPdf output(String path) {
        this.config.setOutputPath(Objects.requireNonNull(path, "Output path cannot be null"));
        return this;
    }

    public DonPdf pageSize(PageSize pageSize) {
        this.config.setPageSize(Objects.requireNonNull(pageSize, "Page size cannot be null"));
        return this;
    }

    public DonPdf backgroundColor(int r, int g, int b) {
        validateRGBValue(r, "Red");
        validateRGBValue(g, "Green");
        validateRGBValue(b, "Blue");
        this.config.setBackgroundColor(new DeviceRgb(r, g, b));
        return this;
    }

    public DonPdf backgroundColor(String color) {
        Objects.requireNonNull(color, "Color cannot be null");
        this.config.setBackgroundColor(ColorParser.parse(color));
        return this;
    }

    public DonPdf backgroundImage(String image) {
        this.config.setBackgroundImagePath(Objects.requireNonNull(image, "Image path cannot be null"));
        return this;
    }

    public DonPdf backgroundOpacity(float opacity) {
        this.config.setBackgroundOpacity(opacity);
        return this;
    }

    public DonPdf defaultFont(String fontPath) throws IOException {
        Objects.requireNonNull(fontPath, "Font path cannot be null");
        this.config.setDefaultFont(loadFont(fontPath));
        return this;
    }

    public DonPdf defaultFont(String fontPath, String encoding, PdfFontFactory.EmbeddingStrategy embeddingStrategy) throws IOException {
        Objects.requireNonNull(fontPath, "Font path cannot be null");
        Objects.requireNonNull(encoding, "Encoding cannot be null");
        Objects.requireNonNull(embeddingStrategy, "EmbeddingStrategy cannot be null");
        this.config.setDefaultFont(loadFont(fontPath, encoding, embeddingStrategy));
        return this;
    }

    public DonPdf defaultFontSize(float size) {
        this.config.setDefaultFontSize(size);
        return this;
    }

    public DonPdf mergePdf(String pdfPath) {
        Objects.requireNonNull(pdfPath, "PDF path cannot be null");
        if (!new File(pdfPath).exists()) throw new IllegalArgumentException("PDF file does not exist: " + pdfPath);
        mergePaths.add(pdfPath);
        return this;
    }

    public void build() throws IOException {
        config.validate();
        if (mergePaths.isEmpty()) buildSingleDocument();
        else buildMergedDocument();
    }

    private void buildSingleDocument() throws IOException {
        try (
                PdfWriter writer = new PdfWriter(config.getOutputPath());
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf, config.getPageSize())
        ) {
            applyDocumentSettings(document);
            addElementsToDocument(document);
        }
    }

    private void buildMergedDocument() throws IOException {
        String tempInitialPath = config.getOutputPath() + INITIAL_SUFFIX;
        try {
            createInitialDocument(tempInitialPath);
            mergeDocuments(tempInitialPath);
        } finally {
            new File(tempInitialPath).delete();
        }
    }

    private void createInitialDocument(String tempPath) throws IOException {
        try (PdfWriter writer = new PdfWriter(tempPath);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf, config.getPageSize())) {

            applyDocumentSettings(document);
            addElementsToDocument(document);
        }
    }

    private void mergeDocuments(String initialPath) throws IOException {
        try (PdfWriter writer = new PdfWriter(config.getOutputPath());
             PdfDocument outputPdf = new PdfDocument(writer)) {

            mergePdfFile(initialPath, outputPdf);
            for (String mergePath : mergePaths) {
                mergePdfFile(mergePath, outputPdf);
            }
        }
    }

    private void mergePdfFile(String pdfPath, PdfDocument outputPdf) throws IOException {
        try (PdfDocument inputPdf = new PdfDocument(new PdfReader(pdfPath))) {
            inputPdf.copyPagesTo(1, inputPdf.getNumberOfPages(), outputPdf);
        }
    }

    private void applyDocumentSettings(Document document) {
        BackgroundUtility.apply(document.getPdfDocument(), config);
    }

    private void addElementsToDocument(Document document) {
        for (Element element : elements) {
            element.addToDocument(document, config.getDefaultFont(), config.getDefaultFontSize());
        }
    }

    private void validateRGBValue(int value, String colorName) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException(String.format("%s value must be between 0 and 255, got: %d", colorName, value));
        }
    }

    private PdfFont loadFont(String fontPath) throws IOException {
        return loadFont(fontPath, "UTF-8", PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
    }

    private PdfFont loadFont(String fontPath, String encoding, PdfFontFactory.EmbeddingStrategy embeddingStrategy) throws IOException {
        FontLoader loader = new FontLoader(fontPath);
        return loader.loadPdfFont(encoding, embeddingStrategy);
    }
}
