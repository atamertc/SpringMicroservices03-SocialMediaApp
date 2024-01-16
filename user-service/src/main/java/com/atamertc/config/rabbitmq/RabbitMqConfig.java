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
    //------------- UserService Exchange -------------//
    @Value("${rabbitmq.user-exchange}")
    private String userExchange;

    @Bean
    DirectExchange userExchange() {
        return new DirectExchange(userExchange);
    }


    //******************* PRODUCER FIELDS AND BEANS *******************//
    //------------- UserService rabbitRegisterUser() -------------//
    @Value("${rabbitmq.elastic-save-binding-key}")
    private String elasticSaveBindingKey;
    @Value("${rabbitmq.elastic-save-queue}")
    private String elasticSaveQueueName;

    @Bean
    Queue elasticSaveQueue() {
        return new Queue(elasticSaveQueueName);
    }
    @Bean
    public Binding activateBinding(final Queue elasticSaveQueue, final DirectExchange userExchange) {
        return BindingBuilder.bind(elasticSaveQueue).to(userExchange).with(elasticSaveBindingKey);
    }




    //******************* CONSUMER FIELDS AND BEANS *******************//


    //------------- AuthService registerWithRabbitmq() -------------//
    @Value("${rabbitmq.register-queue}")
    private String registerQueueName;

    @Bean
    Queue registerQueue() {
        return new Queue(registerQueueName);
    }

    //------------- AuthService activateStatus() -------------//
    @Value("${rabbitmq.activate-queue}")
    private String activateQueueName;

    @Bean
    Queue activateQueue() {
        return new Queue(activateQueueName);
    }

}
