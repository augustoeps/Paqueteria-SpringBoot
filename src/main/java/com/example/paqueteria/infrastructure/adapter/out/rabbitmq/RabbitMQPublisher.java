package com.example.paqueteria.infrastructure.adapter.out.rabbitmq;

import com.example.paqueteria.application.port.out.estadisticas.PaqueteEstadisticaEvento;
import com.example.paqueteria.application.port.out.notifications.EventPublisherPort;
import com.example.paqueteria.application.port.out.notifications.NotificacionEmailEvento;
import com.example.paqueteria.application.port.out.pdf.CreatePdfEvento;
import com.example.paqueteria.infrastructure.config.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

// Esta clase es el "Publisher" real: la pieza de infraestructura que
// sabe cómo hablar con RabbitMQ para publicar mensajes.
// No contiene ninguna lógica de negocio, solo sabe "empaquetar y enviar".
@Component
public class RabbitMQPublisher implements EventPublisherPort {

    // RabbitTemplate es el objeto que Spring AMQP crea automáticamente
    // (auto-configuración) para poder comunicarse con el broker de RabbitMQ.
    // Es el equivalente, en este contexto, a lo que JpaRepository es para la BD,
    // o JavaMailSender es para el correo: la herramienta que hace el trabajo real.
    private final RabbitTemplate rabbitTemplate;

    // Inyección de dependencias por constructor, mismo patrón de siempre.
    // Spring resuelve automáticamente qué instancia de RabbitTemplate pasar aquí.
    public RabbitMQPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Método público que cualquier otra parte de la aplicación (a través
    // de un puerto que aún vamos a construir) puede usar para publicar un evento.
    public void publicarEmail(NotificacionEmailEvento evento) {

        // convertAndSend hace dos cosas en una sola llamada:
        // 1. CONVIERTE el objeto "evento" a su representación en bytes,
        //    usando el MessageConverter configurado (el que armamos para JSON).
        // 2. ENVÍA ese mensaje ya convertido al Exchange indicado,
        //    con la routing key indicada, para que el Exchange decida
        //    (usando los Bindings ya configurados) a qué cola(s) entregarlo.
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,   // A qué Exchange se entrega el mensaje
                RabbitMQConfig.ROUTING_KEY_NOTIFICACION,     // Con qué "etiqueta" viaja el mensaje
                evento                          // El contenido real del mensaje
        );
    }

    @Override
    public void publicarPdf(CreatePdfEvento evento) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,   // A qué Exchange se entrega el mensaje
                RabbitMQConfig.ROUTING_KEY_GENERAR_PDF,     // Con qué "etiqueta" viaja el mensaje
                evento                          // El contenido real del mensaje
        );

    }

    @Override
    public void publicarEstadistica(PaqueteEstadisticaEvento evento) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,   // A qué Exchange se entrega el mensaje
                RabbitMQConfig.ROUTING_KEY_ESTADISTICAS,     // Con qué "etiqueta" viaja el mensaje
                evento                          // El contenido real del mensaje
        );
    }
}