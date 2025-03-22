package com.jaac.pdf.config;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class PdfConfig {
    private String outputPath;
    private PageSize pageSize;
    private Color backgroundColor;
    private String backgroundImagePath;
    private float backgroundOpacity;
    private PdfFont defaultFont;
    private float defaultFontSize;

    public void validate() {
        if (outputPath == null || outputPath.trim().isEmpty()) throw new IllegalStateException("Output path must be set before building");
        ensureDirectoryExists(outputPath);
    }

    private void ensureDirectoryExists(String filePath) {
        Path directory = Paths.get(filePath).getParent();
        if (directory != null) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create directory: " + directory, e);
            }
        }
    }

    public String getOutputPath() {return outputPath;}

    public PageSize getPageSize() {return pageSize;}

    public Color getBackgroundColor() {return backgroundColor;}

    public String getBackgroundImagePath() {return backgroundImagePath;}

    public float getBackgroundOpacity() {return backgroundOpacity;}

    public PdfFont getDefaultFont() {return defaultFont;}

    public float getDefaultFontSize() {return defaultFontSize;}

    public void setOutputPath(String outputPath) {
        this.outputPath = Objects.requireNonNull(outputPath, "Output path cannot be null");
    }

    public void setPageSize(PageSize pageSize) {
        this.pageSize = Objects.requireNonNull(pageSize, "Page size cannot be null");
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBackgroundImagePath(String backgroundImagePath) {
        this.backgroundImagePath = Objects.requireNonNull(backgroundImagePath, "Background image path cannot be null");
    }

    public void setBackgroundOpacity(float opacity) {
        if (opacity < 0.0f || opacity > 1.0f) throw new IllegalArgumentException("Opacity must be between 0.0 and 1.0");
        this.backgroundOpacity = opacity;
    }

    public void setDefaultFont(PdfFont defaultFont) {
        this.defaultFont = defaultFont;
    }

    public void setDefaultFontSize(float defaultFontSize) {
        if (defaultFontSize <= 0) throw new IllegalArgumentException("Font size must be positive");
        this.defaultFontSize = defaultFontSize;
    }
}
