package com.jaac.pdf.property;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.properties.HorizontalAlignment;

public class ImageProperty extends BaseProperty {
    private final Float width;
    private final Float height;
    private final HorizontalAlignment alignment;
    private final Border border;

    private ImageProperty(Builder builder) {
        super(builder);
        this.width = builder.width;
        this.height = builder.height;
        this.alignment = builder.alignment;
        this.border = builder.border;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends BaseBuilder<Builder> {
        private Float width;
        private Float height;
        private HorizontalAlignment alignment;
        private Border border;

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

        public Builder border(Border border) {
            this.border = border;
            return this;
        }

        public ImageProperty build() {
            return new ImageProperty(this);
        }
    }

    public float getWidth() {return width;}

    public float getHeight() {return height;}

    public HorizontalAlignment getAlignment() {return alignment;}

    public Border getBorder() {return border;}
}
