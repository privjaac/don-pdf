package com.jaac.pdf.parser;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;

import java.lang.reflect.Field;
import java.util.Objects;

public class ColorParser {
    public static Color parse(String color) {
        Objects.requireNonNull(color, "Color cannot be null");
        String colorTrimmed = color.trim().toUpperCase();
        try {
            java.awt.Color awtColor;
            try {
                Field field = java.awt.Color.class.getField(colorTrimmed);
                awtColor = (java.awt.Color) field.get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                if (colorTrimmed.startsWith("#")) {
                    awtColor = java.awt.Color.decode(colorTrimmed);
                } else if (colorTrimmed.matches("[0-9A-F]{6}")) {
                    awtColor = java.awt.Color.decode("#" + colorTrimmed);
                } else {
                    throw new IllegalArgumentException("Unrecognized color format");
                }
            }
            return new DeviceRgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid color: " + color + " Use a color name (e.g., 'RED') or hex code (e.g., '#FF0000')", e);
        }
    }
}
