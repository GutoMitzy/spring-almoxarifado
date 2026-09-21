package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.client.NotificationClient;
import com.br.almoxarifado.almoxarifado.database.model.NotificationModel;
import com.br.almoxarifado.almoxarifado.database.repository.INotificationRepository;
import com.br.almoxarifado.almoxarifado.dto.NotificationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final RabbitTemplate rabbitTemplate;

    private final INotificationRepository notificationRepository;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public void sendNotification(NotificationDto notificationDto) {
        notificationRepository.save(new NotificationModel(notificationDto));
        rabbitTemplate.convertAndSend(exchangeName, routingKey, notificationDto);
    }

    public Page<NotificationDto> getAllNotifications(Integer page, Integer size) {
        return notificationRepository.findAllByOrderByDataEmissaoAsc(PageRequest.of(page, size)).map(NotificationDto::toDto);
    }
}
