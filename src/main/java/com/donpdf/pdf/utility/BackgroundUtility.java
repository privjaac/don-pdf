package com.donpdf.pdf.utility;

import com.donpdf.pdf.config.PdfConfig;
import com.donpdf.pdf.handler.ColorBackgroundHandler;
import com.donpdf.pdf.handler.ImageBackgroundHandler;
import com.itextpdf.kernel.pdf.PdfDocument;

import static com.itextpdf.kernel.pdf.event.PdfDocumentEvent.START_PAGE;

public class BackgroundUtility {
    public static void apply(PdfDocument pdf, PdfConfig config) {
        if (config.getBackgroundColor() != null) {
            pdf.addEventHandler(START_PAGE, new ColorBackgroundHandler(config.getBackgroundColor()));
        }
        if (config.getBackgroundImagePath() != null) {
            pdf.addEventHandler(START_PAGE, new ImageBackgroundHandler(config.getBackgroundImagePath(), config.getBackgroundOpacity()));
        }
    }
}
