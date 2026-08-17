package com.br.almoxarifado.almoxarifado.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "notification-service")
public interface NotificationClient {

    @PostMapping("/v2/almoxarifado/notify")
    void sendNotification(@RequestBody String message);
}
