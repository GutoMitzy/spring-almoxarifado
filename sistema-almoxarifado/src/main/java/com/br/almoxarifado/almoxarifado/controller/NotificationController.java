package com.br.almoxarifado.almoxarifado.controller;

import com.br.almoxarifado.almoxarifado.dto.NotificationDto;
import com.br.almoxarifado.almoxarifado.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/almoxarifado/messages")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendNotification(@RequestBody NotificationDto notificationDto) {
        notificationService.sendNotification(notificationDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<NotificationDto> getAllNotifications(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                     @RequestParam(required = false, defaultValue = "10") Integer size) {
        return notificationService.getAllNotifications(page, size);
    }

}
