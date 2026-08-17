package com.br.almoxarifado.almoxarifado.controller;

import com.br.almoxarifado.almoxarifado.dto.EntradaEstoqueDto;
import com.br.almoxarifado.almoxarifado.service.EstoqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/almoxarifado/entradas")
@RequiredArgsConstructor
@Validated
public class EntradaEstoqueController {

    private final EstoqueService estoqueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEntradaEstoque(@Valid @RequestBody EntradaEstoqueDto entradaEstoqueDto) {
        estoqueService.createEntradaEstoque(entradaEstoqueDto);
    }

    @PatchMapping("/{id}/finish")
    @ResponseStatus(HttpStatus.OK)
    public void concluirEntradaEstoque(@PathVariable Integer id) {
        estoqueService.concluirEntradaEstoque(id);
    }

}
