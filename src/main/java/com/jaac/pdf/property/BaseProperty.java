package com.jaac.pdf.property;

@SuppressWarnings("unchecked")
public abstract class BaseProperty {
    protected final Float marginTop;
    protected final Float marginBottom;
    protected final Float marginLeft;
    protected final Float marginRight;

    protected BaseProperty(BaseBuilder<?> builder) {
        this.marginTop = builder.marginTop;
        this.marginBottom = builder.marginBottom;
        this.marginLeft = builder.marginLeft;
        this.marginRight = builder.marginRight;
    }

    public Float getMarginTop() {return marginTop;}

    public Float getMarginBottom() {return marginBottom;}

    public Float getMarginLeft() {return marginLeft;}

    public Float getMarginRight() {return marginRight;}

    protected abstract static class BaseBuilder<T extends BaseBuilder<T>> {
        protected Float marginTop;
        protected Float marginBottom;
        protected Float marginLeft;
        protected Float marginRight;

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
    }
}
