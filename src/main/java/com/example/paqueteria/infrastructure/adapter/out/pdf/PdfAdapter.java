package com.example.paqueteria.infrastructure.adapter.out.pdf;

import com.example.paqueteria.application.port.out.pdf.CreatePdfEvento;
import com.example.paqueteria.application.port.out.pdf.PdfPort;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PdfAdapter implements PdfPort {

    private static final String CARPETA_SALIDA = "etiquetas/";

    @Override
    public void crear(CreatePdfEvento evento) {
        try (PDDocument documento = new PDDocument()) {

            PDPage pagina = new PDPage();
            documento.addPage(pagina);

            try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
                contenido.beginText();
                contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
                contenido.newLineAtOffset(50, 750);
                contenido.showText("Etiqueta de Envío");
                contenido.endText();

                contenido.beginText();
                contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contenido.newLineAtOffset(50, 700);
                contenido.showText("Remitente: " + evento.remitente());
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Destinatario: " + evento.destinatario());
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Peso: " + evento.peso() + " kg");
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Costo: " + evento.costo());
                contenido.newLineAtOffset(0, -20);
                contenido.showText("Fecha: " + evento.fecha());
                contenido.endText();
            }

            String nombreArchivo = CARPETA_SALIDA + "etiqueta-" + System.currentTimeMillis() + ".pdf";
            documento.save(nombreArchivo);

        } catch (IOException e) {
            throw new RuntimeException("Error al generar el PDF de la etiqueta", e);
        }
    }
}