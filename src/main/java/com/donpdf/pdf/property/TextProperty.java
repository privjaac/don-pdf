package com.donpdf.pdf.property;

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
    private final Boolean isBold;
    private final Boolean isItalic;
    private final Boolean isUnderlined;
    private final Float wordSpacing;
    private final String hyperlinkUrl;

    public TextProperty(Builder builder) {
        super(builder);
        this.font = builder.font;
        this.fontSize = builder.fontSize;
        this.color = builder.color;
        this.alignment = builder.alignment;
        this.border = builder.border;
        this.isBold = builder.isBold;
        this.isItalic = builder.isItalic;
        this.isUnderlined = builder.isUnderline;
        this.wordSpacing = builder.wordSpacing;
        this.hyperlinkUrl = builder.hyperlinkUrl;
    }

    public static Builder builder() {
        return new Builder();
    }

    public PdfFont getFont() {return font;}

    public Float getFontSize() {return fontSize;}

    public Color getColor() {return color;}

    public TextAlignment getAlignment() {return alignment;}

    public Border getBorder() {return border;}

    public Boolean getIsBold() {return isBold;}

    public Boolean getIsUnderlined() {return isUnderlined;}

    public Boolean getIsItalic() {return isItalic;}

    public Float getWordSpacing() {return wordSpacing;}

    public String getHyperlinkUrl() {return hyperlinkUrl;}

    public static class Builder extends BaseBuilder<Builder> {
        private PdfFont font;
        private Float fontSize;
        private Color color;
        private TextAlignment alignment;
        private Border border;
        private Boolean isBold;
        private Boolean isItalic;
        private Boolean isUnderline;
        private Float wordSpacing;
        private String hyperlinkUrl;

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

        public Builder isBold(Boolean isBold) {
            this.isBold = isBold;
            return this;
        }

        public Builder isItalic(Boolean isItalic) {
            this.isItalic = isItalic;
            return this;
        }

        public Builder isUnderline(Boolean isUnderline) {
            this.isUnderline = isUnderline;
            return this;
        }

        public Builder wordSpacing(Float wordSpacing) {
            this.wordSpacing = wordSpacing;
            return this;
        }

        public Builder hyperlinkUrl(String hyperlinkUrl) {
            this.hyperlinkUrl = hyperlinkUrl;
            return this;
        }

        public TextProperty build() {
            return new TextProperty(this);
        }
    }
}
