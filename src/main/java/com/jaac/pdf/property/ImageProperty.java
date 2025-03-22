package com.jaac.pdf.property;

import com.itextpdf.layout.properties.HorizontalAlignment;

public class ImageProperty extends BaseProperty {
    private final Float width;
    private final Float height;
    private final HorizontalAlignment alignment;

    private ImageProperty(Builder builder) {
        super(builder);
        this.width = builder.width;
        this.height = builder.height;
        this.alignment = builder.alignment;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends BaseBuilder<Builder> {
        private Float width;
        private Float height;
        private HorizontalAlignment alignment;

        private Builder() {
            super();
        }

        public Builder size(Float width, Float height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder alignment(HorizontalAlignment alignment) {
            this.alignment = alignment;
            return this;
        }

        public ImageProperty build() {
            return new ImageProperty(this);
        }
    }

    public float getWidth() {return width;}

    public float getHeight() {return height;}

    public HorizontalAlignment getAlignment() {return alignment;}
}
