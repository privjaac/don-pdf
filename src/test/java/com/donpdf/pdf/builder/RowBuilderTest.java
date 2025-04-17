package com.donpdf.pdf.builder;

import com.donpdf.pdf.main.DonPdf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RowBuilderTest {
    @TempDir
    Path tempDir;
    private String outputPath;
    private DonPdf donPdf;

    @BeforeEach
    void setUp() {
        outputPath = tempDir.resolve("row-test.pdf").toString();
        donPdf = DonPdf.builder().output(outputPath);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(outputPath));
    }

    @Test
    void testBasicRowCreation() throws IOException {
        donPdf
                .addTable(100, 100)
                .addRow()
                .addCell().content("Cell 1")
                .addCell().content("Cell 2")
                .endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);
    }

    @Test
    void testMultipleRows() throws IOException {
        donPdf
                .addTable(100, 100)
                .addRow()
                .addCell().content("Row 1, Cell 1")
                .addCell().content("Row 1, Cell 2").endRow()
                .addRow()
                .addCell().content("Row 2, Cell 1")
                .addCell().content("Row 2, Cell 2").endRow()
                .addRow()
                .addCell().content("Row 3, Cell 1")
                .addCell().content("Row 3, Cell 2").endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testRowWithMultipleCells() throws IOException {
        donPdf
                .addTable(50, 50, 50, 50, 50)
                .addRow()
                .addCell().content("Cell 1")
                .addCell().content("Cell 2")
                .addCell().content("Cell 3")
                .addCell().content("Cell 4")
                .addCell().content("Cell 5").endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testRowWithFormattedCells() throws IOException {
        donPdf
                .addTable(150, 150)
                .addRow()
                .addCell()
                .content("Formatted Cell 1")
                .fontSize(14)
                .color("BLUE")
                .alignmentCenter()
                .addCell()
                .content("Formatted Cell 2")
                .fontSize(14)
                .color("RED")
                .alignmentCenter()
                .endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testEmptyRow() throws IOException {
        donPdf
                .addTable(100, 100)
                .addRow()
                .endRow()
                .addRow()
                .addCell().content("Cell after empty row")
                .addCell().content("Another cell").endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testMixedRowCellSizes() throws IOException {
        donPdf
                .addTable(50, 100, 150)
                .addRow()
                .addCell().content("Small")
                .addCell().content("Medium")
                .addCell().content("Large").endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testRowsWithBackground() throws IOException {
        donPdf
                .addTable(100, 100)
                .addRow()
                .addCell().content("Row 1, Cell 1").background("GRAY")
                .addCell().content("Row 1, Cell 2").background("GRAY").endRow()
                .addRow()
                .addCell().content("Row 2, Cell 1")
                .addCell().content("Row 2, Cell 2").endRow()
                .addRow()
                .addCell().content("Row 3, Cell 1").background("GRAY")
                .addCell().content("Row 3, Cell 2").background("GRAY").endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testAddingCellsDirectlyWithoutEndRowCall() throws IOException {
        TableBuilder tableBuilder = donPdf.addTable(100, 100);
        RowBuilder rowBuilder = tableBuilder.addRow();
        rowBuilder.addCell().content("Cell 1");
        rowBuilder.addCell().content("Cell 2").endRow();
        // tableBuilder.endRow();
        tableBuilder.addRow();
        tableBuilder.next();
        donPdf.build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testCellReferencesInRow() throws IOException {
        donPdf
                .addTable(100, 100)
                .addRow()
                .addCell()
                .content("First Cell")
                .alignmentCenter()
                .addCell()
                .content("Second Cell")
                .alignmentCenter()
                .endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }
}
