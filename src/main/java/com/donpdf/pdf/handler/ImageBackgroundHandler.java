package com.donpdf.pdf.handler;

import com.donpdf.pdf.loader.BackgroundImageLoader;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteUtils;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEvent;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEventHandler;
import com.itextpdf.kernel.pdf.event.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;

public class ImageBackgroundHandler extends AbstractPdfDocumentEventHandler {
    private final BackgroundImageLoader imageLoader;
    private final float opacity;

    public ImageBackgroundHandler(String imagePath, float opacity) {
        this.imageLoader = new BackgroundImageLoader(imagePath);
        this.opacity = opacity;
    }

    @Override
    protected void onAcceptedEvent(AbstractPdfDocumentEvent event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        drawBackground(docEvent.getDocument(), docEvent.getPage());
    }

    private void drawBackground(PdfDocument pdf, PdfPage page) {
        byte[] imageData = imageLoader.loadImageData();
        ImageData backgroundImage = ImageDataFactory.create(imageData);
        float pageWidth = pdf.getDefaultPageSize().getWidth();
        float pageHeight = pdf.getDefaultPageSize().getHeight();
        float imageWidth = backgroundImage.getWidth();
        float imageHeight = backgroundImage.getHeight();
        // Calculate scaling to cover the entire page
        float scale = Math.max(pageWidth / imageWidth, pageHeight / imageHeight);
        float finalWidth = imageWidth * scale;
        float finalHeight = imageHeight * scale;
        // Center the image
        float x = (pageWidth - finalWidth) / 2;
        float y = (pageHeight - finalHeight) / 2;
        PdfCanvas canvas = new PdfCanvas(page);
        canvas.saveState();
        // Apply opacity
        PdfExtGState gs1 = new PdfExtGState();
        gs1.setFillOpacity(opacity);
        canvas.setExtGState(gs1);
        // Position and scale the image
        canvas.concatMatrix(finalWidth, 0, 0, finalHeight, x, y);
        // Add image to resources and draw
        PdfImageXObject imageXObject = new PdfImageXObject(backgroundImage);
        PdfName imageName = canvas.getResources().addImage(imageXObject);
        canvas.getContentStream()
                .getOutputStream()
                .write(imageName)
                .writeBytes(ByteUtils.getIsoBytes(" Do\n"));
        canvas.restoreState();
    }
}
