package com.example.paqueteria.infrastructure.adapter.out.rabbitmq;

import com.example.paqueteria.application.port.out.notifications.NotificacionEmailEvento;
import com.example.paqueteria.infrastructure.config.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacionSmsListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_SMS_NAME)
    public void escuchar(NotificacionEmailEvento evento){
        System.out.println(">>> SMS SIMULADO enviado a: " + evento.destinatario());
        System.out.println(">>> Contenido: " + evento.asunto() + " - " + evento.cuerpo());
    }


}
