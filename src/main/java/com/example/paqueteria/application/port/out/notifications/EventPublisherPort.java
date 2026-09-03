package com.example.paqueteria.application.port.out.notifications;

import com.example.paqueteria.application.port.out.estadisticas.PaqueteEstadisticaEvento;
import com.example.paqueteria.application.port.out.pdf.CreatePdfEvento;

public interface EventPublisherPort {
    void publicarEmail(NotificacionEmailEvento evento);

    void publicarPdf(CreatePdfEvento evento);

    void publicarEstadistica(PaqueteEstadisticaEvento evento);

}
