package com.donpdf.pdf.builder;

import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.properties.TextAlignment;
import com.donpdf.pdf.main.DonPdf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CellBuilderTest {
    @TempDir
    Path tempDir;
    private String outputPath;
    private DonPdf donPdf;

    @BeforeEach
    void setUp() {
        outputPath = tempDir.resolve("cell-test.pdf").toString();
        donPdf = DonPdf.builder().output(outputPath);
    }

    @Test
    void testBasicCellCreation() throws IOException {
        donPdf
                .addTable(100, 100)
                .addRow()
                .addCell()
                .content("Basic cell")
                .endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);
    }

    @Test
    void testCellWithFontSize() throws IOException {
        donPdf
                .addTable(200)
                .addRow()
                .addCell()
                .content("Cell with font size")
                .fontSize(16f)
                .endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testCellWithColor() throws IOException {
        donPdf
                .addTable(200)
                .addRow()
                .addCell()
                .content("Cell with color")
                .color("RED")
                .endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testCellWithRGBColor() throws IOException {
        donPdf
                .addTable(200)
                .addRow()
                .addCell()
                .content("Cell with RGB color")
                .color(0, 128, 0)
                .endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testCellWithBackgroundColor() throws IOException {
        donPdf
                .addTable(200)
                .addRow()
                .addCell()
                .content("Cell with background")
                .background("GRAY")
                .endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testCellWithBackgroundRGBColor() throws IOException {
        donPdf
                .addTable(200)
                .addRow()
                .addCell()
                .content("Cell with RGB background")
                .background(200, 230, 255)
                .endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testCellWithAlignment() throws IOException {
        donPdf
                .addTable(300)
                .addRow()
                .addCell()
                .content("Left aligned")
                .alignmentLeft()
                .endRow()
                .addRow()
                .addCell()
                .content("Center aligned")
                .alignmentCenter()
                .endRow()
                .addRow()
                .addCell()
                .content("Right aligned")
                .alignmentRight()
                .endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testCellWithJustifiedAlignment() throws IOException {
        donPdf
                .addTable(300)
                .addRow()
                .addCell()
                .content("This is a longer text that will be justified to demonstrate the justification capabilities of the cell text alignment.")
                .alignmentJustified()
                .endRow()
                .addRow()
                .addCell()
                .content("This text will use justified all alignment which affects all lines including the last one.")
                .alignmentJustifiedAll()
                .endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testCellWithBorder() throws IOException {
        donPdf
                .addTable(200)
                .addRow()
                .addCell()
                .content("Cell with border")
                .border(new SolidBorder(1))
                .endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testMultipleCellsInRow() throws IOException {
        donPdf
                .addTable(100, 100, 100)
                .addRow()
                .addCell()
                .content("Cell 1")
                .addCell()
                .content("Cell 2")
                .addCell()
                .content("Cell 3")
                .endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testCellAlignmentWithDirectMethod() throws IOException {
        donPdf
                .addTable(200)
                .addRow()
                .addCell()
                .content("Cell with alignment")
                .alignment(TextAlignment.CENTER)
                .endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testInvalidRGBValue() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> donPdf
                        .addTable(200)
                        .addRow()
                        .addCell()
                        .content("Invalid color")
                        .color(300, 0, 0)
                        .endRow()
                        .next()
        );
        assertTrue(exception.getMessage().contains("Red value must be between 0 and 255"));
    }
}
