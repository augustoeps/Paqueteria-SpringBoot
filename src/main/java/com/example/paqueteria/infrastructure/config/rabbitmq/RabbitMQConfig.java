package com.example.paqueteria.infrastructure.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Nombre de la cola donde esperarán los mensajes hasta que un Consumer los procese.
    public static final String QUEUE_NAME = "paquete.notificaciones";

    // Nombre del Exchange: el "clasificador" al que el Publisher entrega el mensaje.
    // El Publisher NUNCA habla directamente con la Cola, siempre con el Exchange.
    public static final String EXCHANGE_NAME = "paquete.exchange";

    // La "etiqueta" que se le pone a cada mensaje, para que el Exchange sepa
    // a qué cola(s) debe enrutarlo, según los Bindings configurados.
    public static final String ROUTING_KEY = "paquete.cambio-estado";

    // Declara la Cola en sí. El segundo parámetro (true) la hace "durable":
    // si RabbitMQ se reinicia, la cola (y su definición) sobrevive,
    // aunque los mensajes que tuviera dependen también de si ELLOS son marcados como persistentes.
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    // Declara el Exchange. TopicExchange es un tipo que enruta mensajes
    // comparando la routing key del mensaje contra un patrón (puede usar comodines
    // como * y # para rutas más complejas, aunque aquí usamos una routing key fija y simple).
    //
    // Es el intermediario obligatorio entre quien PUBLICA (Publisher) y quien RECIBE (Cola).
    // Sin Exchange, un mensaje nunca podría llegar a ninguna cola.
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // El Binding es la "regla de conexión" entre el Exchange y la Cola:
    // le dice a RabbitMQ "todo mensaje que llegue a EXCHANGE_NAME con la
    // routing key ROUTING_KEY, entrégalo a la cola QUEUE_NAME".
    //
    // Sin este Binding, el Exchange recibiría el mensaje pero no sabría
    // a qué cola mandarlo, y el mensaje se perdería (o sería descartado según configuración).
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}