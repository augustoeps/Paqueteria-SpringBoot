package com.example.paqueteria.application.port.out.notifications;

public record NotificacionEmailEvento(
        String destinatario,
        String asunto,
        String cuerpo
) {
}
