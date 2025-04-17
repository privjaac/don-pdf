package com.donpdf.pdf.main;

import com.itextpdf.kernel.geom.PageSize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DonPdfTest {
    @TempDir
    Path tempDir;
    private String outputPath;

    @BeforeEach
    void setUp() {
        outputPath = tempDir.resolve("test.pdf").toString();
    }

    @AfterEach
    void tearDown() {
        boolean flag = new File(outputPath).delete();
        System.out.println("tearDown: ¿El archivo se ha eliminado? " + flag);
    }

    @Test
    void testBasicPdfCreation() throws IOException {
        DonPdf.builder()
                .output(outputPath)
                .addText()
                .content("Test Content")
                .fontSize(12.0f)
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);
    }

    @Test
    void testPageSizeSetting() throws IOException {
        DonPdf.builder()
                .output(outputPath)
                .pageSize(PageSize.A3)
                .addText()
                .content("Test Content with A3 Page Size")
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testBackgroundColorRGB() throws IOException {
        DonPdf.builder()
                .output(outputPath)
                .backgroundColor(255, 200, 200)
                .addText()
                .content("Test with RGB Background Color")
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testBackgroundColorName() throws IOException {
        DonPdf.builder()
                .output(outputPath)
                .backgroundColor("BLUE")
                .addText()
                .content("Test with Named Background Color")
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testBackgroundColorHex() throws IOException {
        DonPdf.builder()
                .output(outputPath)
                .backgroundColor("#FF5733")
                .addText()
                .content("Test with Hex Background Color")
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testInvalidRGBValues() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> DonPdf.builder().backgroundColor(300, 0, 0)
        );
        assertTrue(exception.getMessage().contains("Red value must be between 0 and 255"));
    }

    @Test
    void testNullOutputPath() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> DonPdf.builder().output(null)
        );
        assertEquals("Output path cannot be null", exception.getMessage());
    }

    @Test
    void testAutoCreateDirectory() throws IOException {
        String nestedPath = tempDir.resolve("nested/folder/test.pdf").toString();

        DonPdf.builder()
                .output(nestedPath)
                .addText()
                .content("Test Content in Nested Directory")
                .next()
                .build();
        File pdfFile = new File(nestedPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testDefaultFontSize() throws IOException {
        DonPdf.builder()
                .output(outputPath)
                .defaultFontSize(18f)
                .addText()
                .content("Text with default font size")
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testInvalidDefaultFontSize() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> DonPdf.builder().defaultFontSize(-1f)
        );
        assertTrue(exception.getMessage().contains("Font size must be positive"));
    }

    @Test
    void testBackgroundOpacity() throws IOException {
        DonPdf.builder()
                .output(outputPath)
                .backgroundColor("RED")
                .backgroundOpacity(0.5f)
                .addText()
                .content("Test with background opacity")
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testInvalidBackgroundOpacity() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> DonPdf.builder().backgroundOpacity(2.0f)
        );
        assertTrue(exception.getMessage().contains("Opacity must be between 0.0 and 1.0"));
    }
}
