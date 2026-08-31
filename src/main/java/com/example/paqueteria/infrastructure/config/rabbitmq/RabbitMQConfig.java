package com.example.paqueteria.infrastructure.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @EnableRabbit activa el procesamiento de las anotaciones @RabbitListener
// en toda la aplicación (sin esto, los Consumers nunca se registrarían).
@Configuration
@EnableRabbit
public class RabbitMQConfig {

    // ==========================================================
    // NOMBRES DE COLAS
    // Cada cola es un "buzón" independiente donde esperan los mensajes
    // hasta que un Consumer (@RabbitListener) los recoja y procese.
    // ==========================================================

    public static final String QUEUE_EMAIL_NAME = "paquete.notificaciones.email";
    public static final String QUEUE_SMS_NAME = "paquete.notificaciones.sms";
    public static final String QUEUE_PDF_NAME = "paquete.generar.pdf";

    // ==========================================================
    // EXCHANGE
    // El Publisher NUNCA le habla directamente a una cola.
    // Siempre publica hacia un Exchange, y es el Exchange quien decide
    // (usando los Bindings de más abajo) a qué cola(s) entregar cada mensaje.
    // Reutilizamos el MISMO Exchange para todos los eventos relacionados
    // con "Paquete", independientemente de su propósito final (email, sms, pdf).
    // ==========================================================

    public static final String EXCHANGE_NAME = "paquete.exchange";

    // ==========================================================
    // ROUTING KEYS
    // Es la "etiqueta" que lleva cada mensaje. El Exchange compara esta
    // etiqueta contra sus Bindings para decidir a qué cola(s) enviarlo.
    //
    // Usamos DOS routing keys distintas a propósito:
    // - Los eventos de notificación (email/sms) usan una,
    // - los eventos de generación de PDF usan otra completamente distinta,
    // así nunca se mezclan entre sí, aunque compartan el mismo Exchange.
    // ==========================================================

    public static final String ROUTING_KEY_NOTIFICACION = "paquete.cambio-estado";
    public static final String ROUTING_KEY_GENERAR_PDF = "paquete.generar-pdf";

    // ----------------------------------------------------------
    // DECLARACIÓN DE COLAS
    // ----------------------------------------------------------

    @Bean
    public Queue emailQueue() {
        return new Queue(QUEUE_EMAIL_NAME, true);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(QUEUE_SMS_NAME, true);
    }

    @Bean
    public Queue pdfQueue() {
        return new Queue(QUEUE_PDF_NAME, true);
    }

    // ----------------------------------------------------------
    // DECLARACIÓN DEL EXCHANGE PRINCIPAL
    // ----------------------------------------------------------

    // TopicExchange: tipo de Exchange que enruta comparando la routing key
    // del mensaje contra los patrones de sus Bindings.
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // ----------------------------------------------------------
    // BINDINGS (las reglas de conexión Exchange → Cola)
    // ----------------------------------------------------------

    // "Todo mensaje que llegue a EXCHANGE_NAME con la routing key de NOTIFICACION,
    // entrégalo a la cola de email."
    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange exchange) {
        return BindingBuilder.bind(emailQueue).to(exchange).with(ROUTING_KEY_NOTIFICACION);
    }

    // Misma routing key que el email: ambos Bindings comparten la misma etiqueta,
    // así que un solo mensaje publicado se ENTREGA A AMBAS colas (broadcast).
    @Bean
    public Binding smsBinding(Queue smsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(smsQueue).to(exchange).with(ROUTING_KEY_NOTIFICACION);
    }

    // Routing key DISTINTA (GENERAR_PDF): solo los mensajes publicados
    // específicamente con esa etiqueta llegan a esta cola — nunca se mezcla
    // con los eventos de notificación, aunque compartan el mismo Exchange.
    @Bean
    public Binding pdfBinding(Queue pdfQueue, TopicExchange exchange) {
        return BindingBuilder.bind(pdfQueue).to(exchange).with(ROUTING_KEY_GENERAR_PDF);
    }
}