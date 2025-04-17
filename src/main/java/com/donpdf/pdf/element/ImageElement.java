package com.donpdf.pdf.element;

import com.donpdf.pdf.loader.BackgroundImageLoader;
import com.donpdf.pdf.property.ImageProperty;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import static java.util.Optional.ofNullable;

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
        byte[] imageData = imageLoader.loadImageData();
        Image image = new Image(ImageDataFactory.create(imageData));
        if (properties.getWidth() > 0 && properties.getHeight() > 0) {
            image.setWidth(properties.getWidth());
            image.setHeight(properties.getHeight());
        }
        ofNullable(properties.getBorder()).ifPresent(image::setBorder);
        ofNullable(properties.getAlignment()).ifPresent(image::setHorizontalAlignment);
        ofNullable(properties.getMarginTop()).ifPresent(image::setMarginTop);
        ofNullable(properties.getMarginRight()).ifPresent(image::setMarginRight);
        ofNullable(properties.getMarginBottom()).ifPresent(image::setMarginBottom);
        ofNullable(properties.getMarginLeft()).ifPresent(image::setMarginLeft);
        document.add(image);
    }
}
