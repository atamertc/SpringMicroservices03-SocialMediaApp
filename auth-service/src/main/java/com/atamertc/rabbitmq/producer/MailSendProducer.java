package com.atamertc.rabbitmq.producer;

import com.atamertc.rabbitmq.model.ActivateUserModel;
import com.atamertc.rabbitmq.model.MailSendModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSendProducer {

    @Value("${rabbitmq.auth-exchange}")
    private String authExchange;
    @Value("${rabbitmq.mail-bindingKey}")
    private String mailBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendMailActivation(MailSendModel model) {
        rabbitTemplate.convertAndSend(authExchange, mailBindingKey, model);
    }


}
