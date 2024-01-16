package com.atamertc.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    //******************* EXCHANGE FIELD AND BEAN *******************//
    //------------- AuthService Exchange -------------//
    @Value("${rabbitmq.auth-exchange}")
    private String authExchange;

    @Bean
    DirectExchange authExchange() {
        return new DirectExchange(authExchange);
    }


    //******************* PRODUCER FIELDS AND BEANS *******************//


    //------------- AuthService registerWithRabbitmq() -------------//
    @Value("${rabbitmq.register-bindingKey}")
    private String registerBindingKey;
    @Value("${rabbitmq.register-queue}")
    private String registerQueueName;

    @Bean
    Queue registerQueue() {
        return new Queue(registerQueueName);
    }
    @Bean
    public Binding registerBinding(final Queue registerQueue, final DirectExchange authExchange) {
        return BindingBuilder.bind(registerQueue).to(authExchange).with(registerBindingKey);
    }

    //------------- AuthService activateStatus() -------------//
    @Value("${rabbitmq.activate-bindingKey}")
    private String activateBindingKey;
    @Value("${rabbitmq.activate-queue}")
    private String activateQueueName;

    @Bean
    Queue activateQueue() {
        return new Queue(activateQueueName);
    }
    @Bean
    public Binding activateBinding(final Queue activateQueue, final DirectExchange authExchange) {
        return BindingBuilder.bind(activateQueue).to(authExchange).with(activateBindingKey);
    }

    //------------- AuthService registerWithRabbitmq() -------------//
    @Value("${rabbitmq.mail-bindingKey}")
    private String mailBindingKey;
    @Value("${rabbitmq.mail-queue}")
    private String mailQueueName;

    @Bean
    Queue mailQueue() {
        return new Queue(mailQueueName);
    }
    @Bean
    public Binding mailBinding(final Queue mailQueue, final DirectExchange authExchange) {
        return BindingBuilder.bind(mailQueue).to(authExchange).with(mailBindingKey);
    }



    //******************* CONSUMER FIELDS AND BEANS *******************//




}
