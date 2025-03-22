package com.jaac.pdf.property;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.properties.TextAlignment;

public class TextProperty extends BaseProperty {
    private final PdfFont font;
    private final Float fontSize;
    private final Color color;
    private final TextAlignment alignment;
    private final Border border;

    private TextProperty(Builder builder) {
        super(builder);
        this.font = builder.font;
        this.fontSize = builder.fontSize;
        this.color = builder.color;
        this.alignment = builder.alignment;
        this.border = builder.border;
    }

    public static Builder builder() {
        return new Builder();
    }

    public PdfFont getFont() {return font;}

    public Float getFontSize() {return fontSize;}

    public Color getColor() {return color;}

    public TextAlignment getAlignment() {return alignment;}

    public Border getBorder() {return border;}

    public static class Builder extends BaseBuilder<Builder> {
        private PdfFont font;
        private Float fontSize;
        private Color color;
        private TextAlignment alignment;
        private Border border;

        private Builder() {
            super();
        }

        public Builder font(PdfFont font) {
            this.font = font;
            return this;
        }

        public Builder fontSize(Float fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public Builder color(Color color) {
            this.color = color;
            return this;
        }

        public Builder alignment(TextAlignment alignment) {
            this.alignment = alignment;
            return this;
        }

        public Builder border(Border border) {
            this.border = border;
            return this;
        }

        public TextProperty build() {
            return new TextProperty(this);
        }
    }
}
