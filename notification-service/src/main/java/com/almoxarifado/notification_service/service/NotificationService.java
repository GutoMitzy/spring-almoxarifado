package com.almoxarifado.notification_service.service;

import com.almoxarifado.notification_service.dto.NotificationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receaveMessage(@Valid NotificationDto notificationDto) {
        messagingTemplate.convertAndSend(
                "/topic/admin/notifications",
                notificationDto
        );
    }
}
