package com.br.almoxarifado.spring_almoxarifado.controller;

import com.br.almoxarifado.spring_almoxarifado.database.model.PecaModel;
import com.br.almoxarifado.spring_almoxarifado.dto.PecaDto;
import com.br.almoxarifado.spring_almoxarifado.service.PecaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/almoxarifado/pecas")
@RequiredArgsConstructor
@Validated
public class PecaController {
    private final PecaService pecaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PecaModel> getAllPecas() {
        return pecaService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPeca(@Valid @RequestBody PecaDto pecaDto) {
        pecaService.createPeca(pecaDto);
    }



}
