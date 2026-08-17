package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.client.NotificationClient;
import com.br.almoxarifado.almoxarifado.dto.NotificationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class NotificationService {
    private final NotificationClient notificationClient;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receaveMessage(@Valid NotificationDto notificationDto) {
        System.out.println("receaveMessage: " + notificationDto);
    }
}
