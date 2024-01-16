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
    @Value("${rabbitmq.elastic-exchange}")
    private String elasticExchange;

    @Bean
    DirectExchange authExchange() {
        return new DirectExchange(elasticExchange);
    }


    //******************* PRODUCER FIELDS AND BEANS *******************//






    //******************* CONSUMER FIELDS AND BEANS *******************//

    //------------- UserService rabbitRegisterUser() -------------//
    @Value("${rabbitmq.elastic-save-queue}")
    private String elasticSaveQueueName;

    @Bean
    Queue elasticSaveQueue() {
        return new Queue(elasticSaveQueueName);
    }


}
