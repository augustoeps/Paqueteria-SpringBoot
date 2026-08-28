package com.example.paqueteria.application.port.out.notifications;

public interface EmailPort {

    void send(String destinatario, String asunto, String cuerpo);
}
