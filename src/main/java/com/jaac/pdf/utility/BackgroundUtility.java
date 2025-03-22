package com.jaac.pdf.utility;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.jaac.pdf.config.PdfConfig;
import com.jaac.pdf.handler.ColorBackgroundHandler;
import com.jaac.pdf.handler.ImageBackgroundHandler;

import static com.itextpdf.kernel.events.PdfDocumentEvent.START_PAGE;

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
