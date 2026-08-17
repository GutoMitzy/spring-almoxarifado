package com.almoxarifado.notification_service.service;

import com.almoxarifado.notification_service.dto.NotificationDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    public void sendNotification(NotificationDto notificationDto) {
        rabbitTemplate.convertAndSend(queueName, notificationDto);
    }
}
