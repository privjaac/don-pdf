package com.jaac.pdf.fragment;

import com.jaac.pdf.property.TextProperty;

public class TextFragment {
    private final String text;
    private TextProperty properties;

    public TextFragment(String text) {
        this.text = text;
    }

    public void setProperties(TextProperty properties) {
        this.properties = properties;
    }

    public String getText() {
        return text;
    }

    public TextProperty getProperties() {
        return properties;
    }
}
