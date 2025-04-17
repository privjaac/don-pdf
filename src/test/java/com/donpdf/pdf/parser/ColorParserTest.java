package com.donpdf.pdf.parser;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ColorParserTest {
    @Test
    void testParseWithPredefinedColor() {
        Color red = ColorParser.parse("RED");
        assertInstanceOf(DeviceRgb.class, red);
        DeviceRgb deviceRgb = (DeviceRgb) red;
        assertEquals(1.0f, deviceRgb.getColorValue()[0], 0.001f);
        assertEquals(0.0f, deviceRgb.getColorValue()[1], 0.001f);
        assertEquals(0.0f, deviceRgb.getColorValue()[2], 0.001f);
    }

    @Test
    void testParseCaseInsensitive() {
        Color blue1 = ColorParser.parse("BLUE");
        Color blue2 = ColorParser.parse("blue");
        Color blue3 = ColorParser.parse("Blue");

        assertNotNull(blue1);
        assertNotNull(blue2);
        assertNotNull(blue3);

        DeviceRgb rgb1 = (DeviceRgb) blue1;
        DeviceRgb rgb2 = (DeviceRgb) blue2;
        DeviceRgb rgb3 = (DeviceRgb) blue3;

        assertEquals(rgb1.getColorValue()[0], rgb2.getColorValue()[0], 0.001f);
        assertEquals(rgb1.getColorValue()[1], rgb2.getColorValue()[1], 0.001f);
        assertEquals(rgb1.getColorValue()[2], rgb2.getColorValue()[2], 0.001f);

        assertEquals(rgb1.getColorValue()[0], rgb3.getColorValue()[0], 0.001f);
        assertEquals(rgb1.getColorValue()[1], rgb3.getColorValue()[1], 0.001f);
        assertEquals(rgb1.getColorValue()[2], rgb3.getColorValue()[2], 0.001f);
    }

    @Test
    void testParseWithHexCode() {
        Color orange = ColorParser.parse("#FFA500");
        assertInstanceOf(DeviceRgb.class, orange);
        DeviceRgb deviceRgb = (DeviceRgb) orange;
        assertEquals(1.0f, deviceRgb.getColorValue()[0], 0.001f);
        assertEquals(0.647f, deviceRgb.getColorValue()[1], 0.001f);
        assertEquals(0.0f, deviceRgb.getColorValue()[2], 0.001f);
    }

    @Test
    void testParseWithHexCodeWithoutHash() {
        Color purple = ColorParser.parse("800080");
        assertInstanceOf(DeviceRgb.class, purple);
        DeviceRgb deviceRgb = (DeviceRgb) purple;
        assertEquals(0.5019608f, deviceRgb.getColorValue()[0], 0.001f);
        assertEquals(0.0f, deviceRgb.getColorValue()[1], 0.001f);
        assertEquals(0.5019608f, deviceRgb.getColorValue()[2], 0.001f);
    }

    @Test
    void testParseWithWhitespace() {
        Color gray = ColorParser.parse(" GRAY ");
        assertInstanceOf(DeviceRgb.class, gray);
    }

    @Test
    void testParseWithInvalidColorName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ColorParser.parse("INVALID_COLOR"));
        assertTrue(exception.getMessage().contains("Invalid color"));
    }

    @Test
    void testParseWithInvalidHexCode() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ColorParser.parse("#GGGGGG");
        });
        assertTrue(exception.getMessage().contains("Invalid color"));
    }

    @ParameterizedTest
    @NullSource
    void testParseWithNull(String colorName) {
        assertThrows(NullPointerException.class, () -> ColorParser.parse(colorName));
    }

    @ParameterizedTest
    @MethodSource("provideCommonColors")
    void testCommonColors(String colorName, int r, int g, int b) {
        Color color = ColorParser.parse(colorName);
        assertInstanceOf(DeviceRgb.class, color);
        DeviceRgb deviceRgb = (DeviceRgb) color;

        float expectedR = r / 255.0f;
        float expectedG = g / 255.0f;
        float expectedB = b / 255.0f;

        assertEquals(expectedR, deviceRgb.getColorValue()[0], 0.002f);
        assertEquals(expectedG, deviceRgb.getColorValue()[1], 0.002f);
        assertEquals(expectedB, deviceRgb.getColorValue()[2], 0.002f);
    }

    private static Stream<Arguments> provideCommonColors() {
        return Stream.of(
                Arguments.of("BLACK", 0, 0, 0),
                Arguments.of("WHITE", 255, 255, 255),
                Arguments.of("RED", 255, 0, 0),
                Arguments.of("GREEN", 0, 255, 0),
                Arguments.of("BLUE", 0, 0, 255),
                Arguments.of("YELLOW", 255, 255, 0),
                Arguments.of("CYAN", 0, 255, 255),
                Arguments.of("MAGENTA", 255, 0, 255),
                Arguments.of("ORANGE", 255, 200, 0),
                Arguments.of("PINK", 255, 175, 175)
        );
    }
}
