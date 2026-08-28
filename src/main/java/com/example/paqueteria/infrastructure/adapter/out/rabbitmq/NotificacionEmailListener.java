package com.example.paqueteria.infrastructure.adapter.out.rabbitmq;

import com.example.paqueteria.application.port.out.notifications.EmailPort;
import com.example.paqueteria.application.port.out.notifications.NotificacionEmailEvento;
import com.example.paqueteria.infrastructure.config.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacionEmailListener {

    private final EmailPort emailPort;


    public NotificacionEmailListener(EmailPort emailPort) {
        this.emailPort = emailPort;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void escuchar(NotificacionEmailEvento evento) {
        emailPort.send(evento.destinatario(), evento.asunto(), evento.cuerpo());
    }


}
