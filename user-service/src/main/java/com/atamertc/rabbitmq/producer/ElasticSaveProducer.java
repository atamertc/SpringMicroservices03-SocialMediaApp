package com.atamertc.rabbitmq.producer;

import com.atamertc.rabbitmq.model.ElasticUserSaveModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElasticSaveProducer {
    @Value("${rabbitmq.user-exchange}")
    private String userExchange;
    @Value("${rabbitmq.elastic-save-binding-key}")
    private String elasticSaveBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendElasticSaveMessage(ElasticUserSaveModel model) {
        rabbitTemplate.convertAndSend(userExchange, elasticSaveBindingKey, model);
    }



}
