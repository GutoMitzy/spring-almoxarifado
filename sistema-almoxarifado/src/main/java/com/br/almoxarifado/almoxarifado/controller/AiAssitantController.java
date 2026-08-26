package com.br.almoxarifado.almoxarifado.controller;

import com.br.almoxarifado.almoxarifado.assistant.factory.AiAssistantFactory;
import com.br.almoxarifado.almoxarifado.assistant.factory.AiAssistantFormat;
import com.br.almoxarifado.almoxarifado.dto.MessageDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/almoxarifado/assistant")
@RequiredArgsConstructor
@Validated
public class AiAssitantController {

    private final AiAssistantFormat aiModel;

    @PostMapping
    public ResponseEntity chat(@Valid @RequestBody MessageDto message) {
        String response = aiModel.chat(message.message());

        return ResponseEntity.ok().body(response);
    }
}
