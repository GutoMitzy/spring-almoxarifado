package com.br.almoxarifado.almoxarifado.controller;

import com.br.almoxarifado.almoxarifado.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/almoxarifado/messages")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

}
