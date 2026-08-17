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

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public void sendNotification(NotificationDto notificationDto) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, notificationDto);
    }
}
