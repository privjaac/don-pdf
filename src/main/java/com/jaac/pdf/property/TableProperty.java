package com.jaac.pdf.property;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.properties.BorderRadius;
import com.itextpdf.layout.properties.HorizontalAlignment;

public class TableProperty extends BaseProperty {
    private final PdfFont font;
    private final Float fontSize;
    private final float width;
    private final HorizontalAlignment alignment;
    private final Border border;
    private final BorderRadius borderRadius;
    private final Color backgroundColor;

    private TableProperty(Builder builder) {
        super(builder);
        this.font = builder.font;
        this.fontSize = builder.fontSize;
        this.width = builder.width;
        this.alignment = builder.alignment;
        this.border = builder.border;
        this.borderRadius = builder.borderRadius;
        this.backgroundColor = builder.backgroundColor;
    }

    public static Builder builder() {
        return new Builder();
    }

    public PdfFont getFont() {return font;}

    public Float getFontSize() {return fontSize;}

    public float getWidth() {return width;}

    public HorizontalAlignment getAlignment() {return alignment;}

    public Border getBorder() {return border;}

    public BorderRadius getBorderRadius() {return borderRadius;}

    public Color getBackgroundColor() {return backgroundColor;}

    public static class Builder extends BaseBuilder<Builder> {
        private PdfFont font;
        private Float fontSize;
        private float width;
        private HorizontalAlignment alignment;
        private Border border;
        private BorderRadius borderRadius;
        private Color backgroundColor;

        public Builder font(PdfFont font) {
            this.font = font;
            return this;
        }

        public Builder fontSize(Float fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public Builder width(float width) {
            this.width = width;
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

        public Builder borderRadius(BorderRadius borderRadius) {
            this.borderRadius = borderRadius;
            return this;
        }

        public Builder backgroundColor(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public TableProperty build() {
            return new TableProperty(this);
        }
    }
}
