package com.almoxarifado.notification_service.service;

import com.almoxarifado.notification_service.dto.NotificationDto;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class NotificationService {

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receaveMessage(@Valid NotificationDto notificationDto) {
        System.out.println("receaveMessage: " + notificationDto);
    }
}
