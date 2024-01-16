package com.atamertc.rabbitmq.producer;

import com.atamertc.rabbitmq.model.ActivateUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateUserProducer {

    @Value("${rabbitmq.auth-exchange}")
    private String authExchange;
    @Value("${rabbitmq.activate-bindingKey}")
    private String activateBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendActivateUser(ActivateUserModel model) {
        rabbitTemplate.convertAndSend(authExchange, activateBindingKey, model);
    }


}
