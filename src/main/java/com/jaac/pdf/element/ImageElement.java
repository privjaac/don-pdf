package com.jaac.pdf.element;

import com.jaac.pdf.loader.BackgroundImageLoader;
import com.jaac.pdf.property.ImageProperty;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.io.IOException;

public class ImageElement implements Element {
    private final String imagePath;
    private final ImageProperty properties;
    private final BackgroundImageLoader imageLoader;

    public ImageElement(String imagePath, ImageProperty properties) {
        this.imagePath = imagePath;
        this.properties = properties;
        this.imageLoader = new BackgroundImageLoader(imagePath);
    }

    @Override
    public void addToDocument(Document document, PdfFont defaultFont, Float defaultFontSize) {
        try {
            byte[] imageData = imageLoader.loadImageData();
            Image image = new Image(ImageDataFactory.create(imageData));
            if (properties.getWidth() > 0 && properties.getHeight() > 0) {
                image.setWidth(properties.getWidth());
                image.setHeight(properties.getHeight());
            }
            if (properties.getAlignment() != null) {
                image.setHorizontalAlignment(properties.getAlignment());
            }
            image.setMargins(
                    properties.getMarginTop(),
                    properties.getMarginRight(),
                    properties.getMarginBottom(),
                    properties.getMarginLeft()
            );
            document.add(image);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar la imagen: " + imagePath, e);
        }
    }
}
