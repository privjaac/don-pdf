package com.donpdf.pdf.builder;

import com.itextpdf.layout.properties.HorizontalAlignment;
import com.donpdf.pdf.main.DonPdf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImageBuilderTest {
    @TempDir
    Path tempDir;
    private String outputPath;
    private DonPdf donPdf;
    private Path testImagePath;

    @BeforeEach
    void setUp() throws IOException {
        outputPath = tempDir.resolve("image-test.pdf").toString();
        donPdf = DonPdf.builder().output(outputPath);
        testImagePath = tempDir.resolve("test-image.png");
        try (InputStream is = getClass().getResourceAsStream("/images/test-image.png")) {
            if (is == null) createDummyImageFile(testImagePath);
            else Files.copy(is, testImagePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(outputPath));
        Files.deleteIfExists(testImagePath);
    }

    private void createDummyImageFile(Path path) throws IOException {
        Files.createFile(path);
    }

    @Test
    void testBasicImageAddition() throws IOException {
        donPdf
                .addImage()
                .path(testImagePath.toString())
                .next()
                .build();

        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);
    }

    @Test
    void testImageWithSize() throws IOException {
        donPdf
                .addImage()
                .path(testImagePath.toString())
                .size(200, 150)
                .next()
                .build();

        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testImageWithAlignment() throws IOException {
        donPdf
                .addImage()
                .path(testImagePath.toString())
                .alignment(HorizontalAlignment.CENTER)
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testImageWithMargins() throws IOException {
        donPdf
                .addImage()
                .path(testImagePath.toString())
                .margins(10, 20, 30, 40)
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testImageWithAllProperties() throws IOException {
        donPdf
                .addImage()
                .path(testImagePath.toString())
                .size(300, 200)
                .alignment(HorizontalAlignment.RIGHT)
                .margins(15, 15, 15, 15)
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testMultipleImages() throws IOException {
        donPdf
                .addImage()
                .path(testImagePath.toString())
                .size(200, 150)
                .alignment(HorizontalAlignment.LEFT)
                .next()
                .addImage()
                .path(testImagePath.toString())
                .size(200, 150)
                .alignment(HorizontalAlignment.RIGHT)
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }

    @Test
    void testImageWithInvalidPath() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> donPdf
                        .addImage()
                        .path("invalid/path/to/image.png")
                        .next()
                        .build()
        );
        assertTrue(exception.getMessage().contains("invalid/path/to/image.png"));
    }

    @Test
    void testImageWithDifferentAlignments() throws IOException {
        donPdf
                .addImage()
                .path(testImagePath.toString())
                .size(150, 100)
                .alignment(HorizontalAlignment.LEFT)
                .next()
                .addText()
                .content("Image with LEFT alignment above")
                .alignmentCenter()
                .next()
                .addImage()
                .path(testImagePath.toString())
                .size(150, 100)
                .alignment(HorizontalAlignment.CENTER)
                .next()
                .addText()
                .content("Image with CENTER alignment above")
                .alignmentCenter()
                .next()
                .addImage()
                .path(testImagePath.toString())
                .size(150, 100)
                .alignment(HorizontalAlignment.RIGHT)
                .next()
                .addText()
                .content("Image with RIGHT alignment above")
                .alignmentCenter()
                .next()
                .build();
        File pdfFile = new File(outputPath);
        assertTrue(pdfFile.exists());
    }
}
