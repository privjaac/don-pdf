package com.donpdf.pdf.builder;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.donpdf.pdf.main.DonPdf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TableBuilderTest {
    @TempDir
    Path tempDir;
    private String outputPath;
    private DonPdf donPdf;
    private PdfFont font;

    record Person(String name, int age, String email) {}

    @BeforeEach
    void setUp() throws IOException {
        outputPath = tempDir.resolve("table-test.pdf").toString();
        donPdf = DonPdf.builder().output(outputPath);
        font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(outputPath));
    }

    @Test
    void testBasicTableCreation() throws IOException {
        donPdf
                .addTable(100, 100)
                .addRow()
                .addCell().content("Header 1")
                .addCell().content("Header 2").endRow()
                .addRow()
                .addCell().content("Data 1")
                .addCell().content("Data 2").endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);
    }

    @Test
    void testTableWithFormatting() throws IOException {
        donPdf
                .addTable(150, 150)
                .width(300)
                .alignment(HorizontalAlignment.CENTER)
                .border(new SolidBorder(1))
                .margins(10, 10, 10, 10)
                .addRow()
                .addCell()
                .content("Formatted Header")
                .fontSize(14)
                .color("BLUE")
                .background("GRAY")
                .alignmentCenter()
                .endRow()
                .addRow()
                .addCell()
                .content("Regular cell")
                .addCell()
                .content("Another cell")
                .endRow()
                .next()
                .build();

        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);
    }

    @Test
    void testDynamicTableGeneration() throws IOException {
        List<Person> personas = Arrays.asList(
                new Person("Juan Pérez", 28, "juan@example.com"),
                new Person("Ana Gómez", 34, "ana@example.com"),
                new Person("Carlos Ruiz", 45, "carlos@example.com")
        );
        TableBuilder tableBuilder = donPdf
                .addTable(150, 100, 200)
                .width(450)
                .alignment(HorizontalAlignment.CENTER);
        tableBuilder.addRow()
                .addCell()
                .content("Nombre")
                .background("GRAY")
                .alignmentCenter()
                .addCell()
                .content("Edad")
                .background("GRAY")
                .alignmentCenter()
                .addCell()
                .content("Email")
                .background("GRAY")
                .alignmentCenter()
                .endRow();

        for (Person persona : personas) {
            tableBuilder.addRow()
                    .addCell()
                    .content(persona.name())
                    .addCell()
                    .content(String.valueOf(persona.age()))
                    .alignmentCenter()
                    .addCell()
                    .content(persona.email())
                    .endRow();
        }
        tableBuilder.next().build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);
    }

    @Test
    void testEmptyTable() throws IOException {
        donPdf
                .addTable(100, 100, 100)
                .width(300)
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testTableWithWidthAndAlignment() throws IOException {
        donPdf
                .addTable(100, 100)
                .width(200)
                .alignment(HorizontalAlignment.CENTER)
                .addRow()
                .addCell().content("Centered Table")
                .addCell().content("With Width").endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testTableWithBorder() throws IOException {
        donPdf
                .addTable(150, 150)
                .border(new SolidBorder(2))
                .addRow()
                .addCell().content("Cell 1")
                .addCell().content("Cell 2").endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testTableWithMargins() throws IOException {
        donPdf
                .addTable(150, 150)
                .margins(20, 30, 40, 50)
                .addRow()
                .addCell().content("Cell with margins")
                .addCell().content("Another cell").endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testTableMultipleRows() throws IOException {
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
    void testTableWithDifferentColumnWidths() throws IOException {
        donPdf
                .addTable(50, 100, 150)
                .addRow()
                .addCell().content("Narrow")
                .addCell().content("Medium")
                .addCell().content("Wide").endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testComplexTable() throws IOException {
        donPdf
                .addTable(150, 100, 150)
                .width(400)
                .alignment(HorizontalAlignment.CENTER)
                .border(new SolidBorder(1))
                .margins(10, 10, 10, 10)
                .addRow()
                .addCell()
                .font(font)
                .content("Producto")
                .fontSize(12)
                .background("GRAY")
                .alignmentCenter()
                .addCell()
                .content("Cantidad")
                .fontSize(12)
                .background("GRAY")
                .alignmentCenter()
                .addCell()
                .content("Precio")
                .fontSize(12)
                .background("GRAY")
                .alignmentCenter()
                .endRow()
                .addRow()
                .addCell()
                .content("Laptop HP")
                .addCell()
                .content("2")
                .alignmentCenter()
                .addCell()
                .content("$1200.00")
                .alignmentRight()
                .endRow()
                .addRow()
                .addCell()
                .content("Monitor Dell")
                .addCell()
                .content("3")
                .alignmentCenter()
                .addCell()
                .content("$350.00")
                .alignmentRight()
                .endRow()
                .addRow()
                .addCell()
                .content("Total")
                .background("BLUE")
                .addCell()
                .content("5")
                .background("BLUE")
                .alignmentCenter()
                .addCell()
                .content("$3450.00")
                .background("BLUE")
                .alignmentRight()
                .endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testManyColumnsTable() throws IOException {
        donPdf
                .addTable(40, 40, 40, 40, 40, 40, 40, 40)
                .width(320)
                .addRow()
                .addCell().content("Col 1")
                .addCell().content("Col 2")
                .addCell().content("Col 3")
                .addCell().content("Col 4")
                .addCell().content("Col 5")
                .addCell().content("Col 6")
                .addCell().content("Col 7")
                .addCell().content("Col 8").endRow()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testAlternatingRowColors() throws IOException {
        TableBuilder tableBuilder = donPdf.addTable(150, 150);
        tableBuilder.addRow()
                .addCell().content("Header 1").background("GRAY")
                .addCell().content("Header 2").background("GRAY")
                .endRow();
        for (int i = 0; i < 5; i++) {
            String bgColor = (i % 2 == 0) ? "WHITE" : "YELLOW";
            tableBuilder.addRow()
                    .addCell().content("Row " + (i + 1) + ", Cell 1").background(bgColor)
                    .addCell().content("Row " + (i + 1) + ", Cell 2").background(bgColor)
                    .endRow();
        }
        tableBuilder.next();
        donPdf.build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }
}
