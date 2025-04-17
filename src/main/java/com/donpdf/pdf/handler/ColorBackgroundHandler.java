package com.donpdf.pdf.handler;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEvent;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEventHandler;
import com.itextpdf.kernel.pdf.event.PdfDocumentEvent;

public class ColorBackgroundHandler extends AbstractPdfDocumentEventHandler {
    private final Color backgroundColor;

    public ColorBackgroundHandler(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    protected void onAcceptedEvent(AbstractPdfDocumentEvent event) {
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
