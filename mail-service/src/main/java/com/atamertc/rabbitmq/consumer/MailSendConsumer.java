package com.atamertc.rabbitmq.consumer;

import com.atamertc.rabbitmq.model.MailSendModel;
import com.atamertc.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSendConsumer {

    private final MailSenderService mailSenderService;

    @RabbitListener(queues = "${rabbitmq.mail-queue}")
    public void listenMailQueue(MailSendModel model) {
        mailSenderService.sendMail(model);
    }


}
