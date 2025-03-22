package com.jaac.pdf.builder;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.jaac.pdf.element.ImageElement;
import com.jaac.pdf.main.DonPdf;
import com.jaac.pdf.property.ImageProperty;

public class ImageBuilder {
    private final DonPdf parent;
    private String path;
    private float width;
    private float height;
    private HorizontalAlignment alignment;
    private float marginTop;
    private float marginRight;
    private float marginBottom;
    private float marginLeft;
    private Border border;

    public ImageBuilder(DonPdf parent) {
        this.parent = parent;
    }

    public ImageBuilder path(String imagePath) {
        this.path = imagePath;
        return this;
    }

    public ImageBuilder size(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public ImageBuilder alignment(HorizontalAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public ImageBuilder alignmentLeft() {
        this.alignment = HorizontalAlignment.LEFT;
        return this;
    }

    public ImageBuilder alignmentCenter() {
        this.alignment = HorizontalAlignment.CENTER;
        return this;
    }

    public ImageBuilder alignmentRight() {
        this.alignment = HorizontalAlignment.RIGHT;
        return this;
    }

    public ImageBuilder margins(float top, float right, float bottom, float left) {
        this.marginTop = top;
        this.marginRight = right;
        this.marginBottom = bottom;
        this.marginLeft = left;
        return this;
    }

    public ImageBuilder border(Border border) {
        this.border = border;
        return this;
    }

    public DonPdf next() {
        ImageProperty properties = ImageProperty
                .builder()
                .size(width, height)
                .alignment(alignment)
                .margins(marginTop, marginRight, marginBottom, marginLeft)
                .border(border)
                .build();
        parent.getElements().add(new ImageElement(path, properties));
        return parent;
    }
}
