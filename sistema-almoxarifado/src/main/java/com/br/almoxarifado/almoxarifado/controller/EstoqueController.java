package com.br.almoxarifado.almoxarifado.controller;

import com.br.almoxarifado.almoxarifado.dto.EntradaEstoqueDto;
import com.br.almoxarifado.almoxarifado.dto.SaidaEstoqueDto;
import com.br.almoxarifado.almoxarifado.dto.StatisticsDto;
import com.br.almoxarifado.almoxarifado.service.EstoqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/almoxarifado/estoque")
@RequiredArgsConstructor
@Validated
public class EstoqueController {

    private final EstoqueService estoqueService;

    @PostMapping("/entradas")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEntradaEstoque(@Valid @RequestBody EntradaEstoqueDto entradaEstoqueDto) {
        estoqueService.createEntradaEstoque(entradaEstoqueDto);
    }

    @PatchMapping("/entradas/{id}/finish")
    @ResponseStatus(HttpStatus.OK)
    public void concluirEntradaEstoque(@PathVariable Integer id) {
        estoqueService.concluirEntradaEstoque(id);
    }

    @PostMapping("/saidas")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSaidaEstoque(@Valid @RequestBody SaidaEstoqueDto saidaEstoqueDto) {
        estoqueService.createSaidaEstoque(saidaEstoqueDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public StatisticsDto getTodayStatistics() {
        return estoqueService.getTodayStatistics();
    }

}
