package com.jaac.pdf.loader;

import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;

public class BackgroundImageLoader extends ResourceLoader {
    private static final String WEBP_EXTENSION = ".webp";
    private static final String PNG_EXTENSION = ".png";
    private static final String TEMP_PREFIX = "temp";

    public BackgroundImageLoader(String path) {
        super(path);
    }

    public byte[] loadImageData() throws IOException {
        byte[] imageData = loadResource();
        return isWebP(resourcePath) ? convertWebPToPNG(imageData) : imageData;
    }

    private boolean isWebP(String path) {
        return path.toLowerCase(Locale.ROOT).endsWith(WEBP_EXTENSION);
    }

    private byte[] convertWebPToPNG(byte[] webpData) throws IOException {
        File tempWebP = File.createTempFile(TEMP_PREFIX, WEBP_EXTENSION);
        File tempPNG = File.createTempFile(TEMP_PREFIX, PNG_EXTENSION);

        try {
            Files.write(tempWebP.toPath(), webpData);
            Mat mat = imread(tempWebP.getAbsolutePath());
            if (mat.empty()) {
                throw new IOException("Failed to read WebP image");
            }

            OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();
            Java2DFrameConverter converterToAWT = new Java2DFrameConverter();
            BufferedImage bufferedImage = converterToAWT.convert(converterToMat.convert(mat));

            ImageIO.write(bufferedImage, "PNG", tempPNG);
            return Files.readAllBytes(tempPNG.toPath());
        } finally {
            tempWebP.delete();
            tempPNG.delete();
        }
    }
}
