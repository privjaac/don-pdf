package com.donpdf.pdf.property;

@SuppressWarnings("unchecked")
public abstract class BaseProperty {
    protected final Float marginTop;
    protected final Float marginBottom;
    protected final Float marginLeft;
    protected final Float marginRight;
    protected final Float paddingTop;
    protected final Float paddingBottom;
    protected final Float paddingLeft;
    protected final Float paddingRight;

    protected BaseProperty(BaseBuilder<?> builder) {
        this.marginTop = builder.marginTop;
        this.marginBottom = builder.marginBottom;
        this.marginLeft = builder.marginLeft;
        this.marginRight = builder.marginRight;
        this.paddingTop = builder.paddingTop;
        this.paddingBottom = builder.paddingBottom;
        this.paddingLeft = builder.paddingLeft;
        this.paddingRight = builder.paddingRight;
    }

    public Float getMarginTop() {return marginTop;}

    public Float getMarginBottom() {return marginBottom;}

    public Float getMarginLeft() {return marginLeft;}

    public Float getMarginRight() {return marginRight;}

    public Float getPaddingTop() {return paddingTop;}

    public Float getPaddingBottom() {return paddingBottom;}

    public Float getPaddingLeft() {return paddingLeft;}

    public Float getPaddingRight() {return paddingRight;}

    protected abstract static class BaseBuilder<T extends BaseBuilder<T>> {
        protected Float marginTop;
        protected Float marginBottom;
        protected Float marginLeft;
        protected Float marginRight;
        protected Float paddingTop;
        protected Float paddingBottom;
        protected Float paddingLeft;
        protected Float paddingRight;

        protected BaseBuilder() {
            this.marginTop = 0.0f;
            this.marginBottom = 0.0f;
            this.marginLeft = 0.0f;
            this.marginRight = 0.0f;
        }

        public T margins(Float top, Float right, Float bottom, Float left) {
            this.marginTop = top;
            this.marginRight = right;
            this.marginBottom = bottom;
            this.marginLeft = left;
            return (T) this;
        }

        public T paddings(Float top, Float right, Float bottom, Float left) {
            this.paddingTop = top;
            this.paddingRight = right;
            this.paddingBottom = bottom;
            this.paddingLeft = left;
            return (T) this;
        }
    }
}
