package com.atamertc.rabbitmq.producer;

import com.atamertc.rabbitmq.model.RegisterUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserProducer {

    @Value("${rabbitmq.auth-exchange}")
    private String authExchange;

    @Value("${rabbitmq.register-bindingKey}")
    private String registerBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendRegisterUserMessage(RegisterUserModel model) {
        rabbitTemplate.convertAndSend(authExchange, registerBindingKey, model);
    }

}
