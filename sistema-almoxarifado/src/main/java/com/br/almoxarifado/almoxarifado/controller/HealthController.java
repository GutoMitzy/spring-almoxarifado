package com.br.almoxarifado.almoxarifado.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/almoxarifado/health")
public class HealthController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> getHealthCheck() {
        return ResponseEntity.ok().build();
    }
}
