package com.example.paqueteria.infrastructure.adapter.out.rabbitmq;


import com.example.paqueteria.application.port.out.notifications.NotificacionEmailEvento;
import com.example.paqueteria.application.port.out.pdf.CreatePdfEvento;
import com.example.paqueteria.application.port.out.pdf.PdfPort;
import com.example.paqueteria.infrastructure.config.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CrearPdfListener {

    private final PdfPort pdfPort;


    public CrearPdfListener(PdfPort pdfPort) {
        this.pdfPort = pdfPort;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PDF_NAME)
    public void escuchar(CreatePdfEvento evento) {
        pdfPort.crear(evento);
    }


}
