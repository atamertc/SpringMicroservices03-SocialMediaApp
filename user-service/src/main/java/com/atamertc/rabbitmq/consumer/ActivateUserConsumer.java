package com.atamertc.rabbitmq.consumer;

import com.atamertc.rabbitmq.model.ActivateUserModel;
import com.atamertc.rabbitmq.model.RegisterUserModel;
import com.atamertc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateUserConsumer {

    private final UserService userService;

    @RabbitListener(queues = "${rabbitmq.activate-queue}")
    public void listenActivateUserQueue(ActivateUserModel model) {
        userService.rabbitActivateUser(model);
    }

}
