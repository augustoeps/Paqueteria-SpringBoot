package com.example.paqueteria.application.port.out.notifications;

public interface EventPublisherPort {
    void publicar(NotificacionEmailEvento evento);
}
