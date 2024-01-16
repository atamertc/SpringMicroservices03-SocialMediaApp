package com.atamertc.rabbitmq.consumer;

import com.atamertc.mapper.UserMapper;
import com.atamertc.rabbitmq.model.ElasticUserSaveModel;
import com.atamertc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElasticSaveUserConsumer {

    private final UserService userService;

    @RabbitListener(queues = "${rabbitmq.elastic-save-queue}")
    public void listenElasticSaveUser(ElasticUserSaveModel model) {
        log.info("User {}", model);
        userService.rabbitSaveElasticUser(model);
    }
}
