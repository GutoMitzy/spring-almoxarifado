package com.br.almoxarifado.almoxarifado.controller;

import com.br.almoxarifado.almoxarifado.dto.SaidaEstoqueDto;
import com.br.almoxarifado.almoxarifado.service.EstoqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/almoxarifado/saidas")
@RequiredArgsConstructor
@Validated
public class SaidaEstoqueController {

    private final EstoqueService estoqueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSaidaEstoque(@Valid @RequestBody SaidaEstoqueDto saidaEstoqueDto) {
        estoqueService.createSaidaEstoque(saidaEstoqueDto);
    }

}
