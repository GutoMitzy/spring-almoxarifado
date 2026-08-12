package com.br.almoxarifado.almoxarifado.controller;

import com.br.almoxarifado.almoxarifado.dto.EntradaEstoqueDto;
import com.br.almoxarifado.almoxarifado.service.EntradaEstoqueService;
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

    private final EntradaEstoqueService entradaEstoqueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEntradaEstoque(@Valid @RequestBody EntradaEstoqueDto entradaEstoqueDto) {
        entradaEstoqueService.createEntradaEstoque(entradaEstoqueDto);
    }

    @PatchMapping("/{id}/finish")
    @ResponseStatus(HttpStatus.OK)
    public void concluirEntradaEstoque(@PathVariable Integer id) {
        entradaEstoqueService.concluirEntradaEstoque(id);
    }

}
