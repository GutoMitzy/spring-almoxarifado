package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.client.NotificationClient;
import com.br.almoxarifado.almoxarifado.dto.NotificationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
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
