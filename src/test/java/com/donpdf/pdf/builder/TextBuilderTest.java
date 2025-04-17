package com.donpdf.pdf.builder;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.properties.TextAlignment;
import com.donpdf.pdf.main.DonPdf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextBuilderTest {
    @TempDir
    Path tempDir;
    private String outputPath;
    private DonPdf donPdf;

    @BeforeEach
    void setUp() {
        outputPath = tempDir.resolve("text-test.pdf").toString();
        donPdf = DonPdf.builder().output(outputPath);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(outputPath));
    }

    @Test
    void testContentMethod() throws IOException {
        donPdf
                .addText()
                .content("Test content method")
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);
    }

    @Test
    void testFontMethod() throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        donPdf
                .addText()
                .content("Test font method")
                .font(font)
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testFontPathMethod() throws IOException {
        donPdf
                .addText()
                .content("Test font path method")
                .font("fonts/test-font.ttf")
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testFontSizeMethod() throws IOException {
        donPdf
                .addText()
                .content("Test font size method")
                .fontSize(18f)
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testColorMethodWithColorObject() throws IOException {
        donPdf
                .addText()
                .content("Test color method with Color object")
                .color(new DeviceRgb(255, 0, 0))  // Red
                .next()
                .build();

        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testColorMethodWithStringName() throws IOException {
        donPdf
                .addText()
                .content("Test color method with string name")
                .color("BLUE")
                .next()
                .build();

        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testColorMethodWithHexCode() throws IOException {
        donPdf
                .addText()
                .content("Test color method with hex code")
                .color("#FF00FF")  // Magenta
                .next()
                .build();

        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testAlignmentMethod() throws IOException {
        donPdf
                .addText()
                .content("Test alignment method")
                .alignment(TextAlignment.CENTER)
                .next()
                .build();

        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testAlignmentLeftMethod() throws IOException {
        donPdf
                .addText()
                .content("Test alignmentLeft method")
                .alignmentLeft()
                .next()
                .build();

        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testAlignmentCenterMethod() throws IOException {
        donPdf
                .addText()
                .content("Test alignmentCenter method")
                .alignmentCenter()
                .next()
                .build();

        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testAlignmentRightMethod() throws IOException {
        donPdf
                .addText()
                .content("Test alignmentRight method")
                .alignmentRight()
                .next()
                .build();

        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testAlignmentJustifiedMethod() throws IOException {
        donPdf
                .addText()
                .content("This is a longer text that will demonstrate the justified alignment. " +
                         "When text is justified, it is aligned to both the left and right margins, " +
                         "creating a clean edge on both sides.")
                .alignmentJustified()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testAlignmentJustifiedAllMethod() throws IOException {
        donPdf
                .addText()
                .content("This is a longer text that will demonstrate the justified all alignment. " +
                         "When text is justified all, it is aligned to both the left and right margins, " +
                         "including the last line of the paragraph.")
                .alignmentJustifiedAll()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testBorderMethod() throws IOException {
        donPdf
                .addText()
                .content("Test border method")
                .border(new SolidBorder(1))
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testMarginsMethod() throws IOException {
        donPdf
                .addText()
                .content("Test margins method")
                .margins(10, 20, 30, 40)
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testNextMethod() throws IOException {
        DonPdf returnedDonPdf = donPdf.addText()
                .content("Test next method")
                .next();
        assertSame(donPdf, returnedDonPdf);
        returnedDonPdf.build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testMultipleTextBuilders() throws IOException {
        donPdf
                .addText()
                .content("First text")
                .fontSize(14f)
                .alignmentLeft()
                .next()
                .addText()
                .content("Second text")
                .fontSize(16f)
                .alignmentCenter()
                .color("RED")
                .next()
                .addText()
                .content("Third text")
                .fontSize(18f)
                .alignmentRight()
                .color("BLUE")
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testCompleteTextFormatting() throws IOException {
        donPdf
                .addText()
                .content("Completely formatted text")
                .fontSize(16f)
                .color("#336699")
                .alignmentCenter()
                .border(new SolidBorder(1))
                .margins(10, 20, 10, 20)
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testLongTextWithBreaks() throws IOException {
        donPdf
                .addText()
                .content("This is a paragraph with a line break.\nThis is the second line.\nAnd this is the third line.")
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testInvalidColorName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> donPdf
                        .addText()
                        .content("Text with invalid color name")
                        .color("NONEXISTENTCOLOR")
                        .next()
        );
        assertTrue(exception.getMessage().contains("Invalid color"));
    }

    @Test
    void testNullContent() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> donPdf.addText().content(null).next().build());
        assertTrue(exception.getMessage().contains("Error al añadir texto al documento"));
    }

    @Test
    void testEmptyContent() throws IOException {
        donPdf
                .addText()
                .content("")
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }
}
