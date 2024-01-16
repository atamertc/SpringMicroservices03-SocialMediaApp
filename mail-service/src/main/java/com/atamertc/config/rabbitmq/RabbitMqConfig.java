package com.atamertc.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqConfig {

    //******************* EXCHANGE FIELD AND BEAN *******************//
    //------------- UserService Exchange -------------//
    @Value("${rabbitmq.mail-exchange}")
    private String mailExchange;

    @Bean
    DirectExchange mailExchange() {
        return new DirectExchange(mailExchange);
    }

    //******************* PRODUCER FIELDS AND BEANS *******************//


    //******************* CONSUMER FIELDS AND BEANS *******************//


    //------------- AuthService registerWithRabbitmq() -------------//
    @Value("${rabbitmq.mail-queue}")
    private String mailQueueName;

    @Bean
    Queue mailQueue() {
        return new Queue(mailQueueName);
    }



}
