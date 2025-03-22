package com.jaac.pdf.config;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PdfConfigTest {
    @TempDir
    Path tempDir;
    private String outputPath;
    private PdfConfig config;

    @BeforeEach
    void setUp() {
        config = new PdfConfig();
        outputPath = tempDir.resolve("test.pdf").toString();
    }

    @Test
    void testSetOutputPath() {
        config.setOutputPath(outputPath);
        assertEquals(outputPath, config.getOutputPath());
    }

    @Test
    void testNullOutputPath() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> config.setOutputPath(null)
        );
        assertEquals("Output path cannot be null", exception.getMessage());
    }

    @Test
    void testSetPageSize() {
        config.setPageSize(PageSize.A4);
        assertEquals(PageSize.A4, config.getPageSize());
    }

    @Test
    void testNullPageSize() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> config.setPageSize(null)
        );
        assertEquals("Page size cannot be null", exception.getMessage());
    }

    @Test
    void testSetBackgroundColor() {
        DeviceRgb color = new DeviceRgb(255, 0, 0);
        config.setBackgroundColor(color);
        assertEquals(color, config.getBackgroundColor());
    }

    @Test
    void testSetBackgroundImagePath() {
        String imagePath = "path/to/image.jpg";
        config.setBackgroundImagePath(imagePath);
        assertEquals(imagePath, config.getBackgroundImagePath());
    }

    @Test
    void testNullBackgroundImagePath() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> config.setBackgroundImagePath(null)
        );
        assertEquals("Background image path cannot be null", exception.getMessage());
    }

    @Test
    void testSetBackgroundOpacity() {
        float opacity = 0.5f;
        config.setBackgroundOpacity(opacity);
        assertEquals(opacity, config.getBackgroundOpacity(), 0.001f);
    }

    @Test
    void testSetBackgroundOpacityOutOfRange() {
        IllegalArgumentException exception1 = assertThrows(
                IllegalArgumentException.class,
                () -> config.setBackgroundOpacity(-0.1f)
        );
        assertTrue(exception1.getMessage().contains("Opacity must be between 0.0 and 1.0"));

        IllegalArgumentException exception2 = assertThrows(
                IllegalArgumentException.class,
                () -> config.setBackgroundOpacity(1.1f)
        );
        assertTrue(exception2.getMessage().contains("Opacity must be between 0.0 and 1.0"));
    }

    @Test
    void testSetDefaultFont() throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        config.setDefaultFont(font);
        assertEquals(font, config.getDefaultFont());
    }

    @Test
    void testSetDefaultFontSize() {
        float fontSize = 12.0f;
        config.setDefaultFontSize(fontSize);
        assertEquals(fontSize, config.getDefaultFontSize(), 0.001f);
    }

    @Test
    void testSetDefaultFontSizeOutOfRange() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> config.setDefaultFontSize(0.0f)
        );
        assertTrue(exception.getMessage().contains("Font size must be positive"));

        IllegalArgumentException exception2 = assertThrows(
                IllegalArgumentException.class,
                () -> config.setDefaultFontSize(-1.0f)
        );
        assertTrue(exception2.getMessage().contains("Font size must be positive"));
    }

    @Test
    void testValidateWithMissingOutputPath() {
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> config.validate()
        );
        assertEquals("Output path must be set before building", exception.getMessage());
    }

    @Test
    void testValidateWithEmptyOutputPath() {
        config.setOutputPath("   ");
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> config.validate()
        );
        assertEquals("Output path must be set before building", exception.getMessage());
    }

    @Test
    void testValidateCreatesDirectory() throws IOException {
        String nestedPath = tempDir.resolve("nested/folder/test.pdf").toString();
        config.setOutputPath(nestedPath);

        config.validate();

        Path directory = Path.of(nestedPath).getParent();
        assertTrue(Files.exists(directory));
        assertTrue(Files.isDirectory(directory));
    }

    @Test
    void testEnsureDirectoryExists() throws IOException {
        String nestedPath = tempDir.resolve("dir1/dir2/test.pdf").toString();
        config.setOutputPath(nestedPath);

        config.validate();

        Path dir1 = tempDir.resolve("dir1");
        Path dir2 = dir1.resolve("dir2");

        assertTrue(Files.exists(dir1));
        assertTrue(Files.isDirectory(dir1));
        assertTrue(Files.exists(dir2));
        assertTrue(Files.isDirectory(dir2));
    }
}
