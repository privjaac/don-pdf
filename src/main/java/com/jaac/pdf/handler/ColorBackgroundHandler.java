package com.jaac.pdf.handler;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

public class ColorBackgroundHandler implements IEventHandler {
    private final Color backgroundColor;

    public ColorBackgroundHandler(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfPage page = docEvent.getPage();
        Rectangle pageSize = page.getPageSize();
        PdfCanvas canvas = new PdfCanvas(page);

        canvas.saveState()
                .setFillColor(backgroundColor)
                .rectangle(
                        pageSize.getLeft(),
                        pageSize.getBottom(),
                        pageSize.getWidth(),
                        pageSize.getHeight()
                )
                .fill()
                .restoreState();
    }
}
