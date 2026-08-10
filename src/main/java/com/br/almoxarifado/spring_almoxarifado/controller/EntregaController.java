package com.br.almoxarifado.spring_almoxarifado.controller;

import com.br.almoxarifado.spring_almoxarifado.database.model.EntregaModel;
import com.br.almoxarifado.spring_almoxarifado.dto.EntregaDto;
import com.br.almoxarifado.spring_almoxarifado.dto.EntregaProjection;
import com.br.almoxarifado.spring_almoxarifado.service.EntregaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/almoxarifado/entregas")
@Validated
@RequiredArgsConstructor
public class EntregaController {
    private final EntregaService entregaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEntrega(@Valid @RequestBody EntregaDto entregaDto) {
        entregaService.createEntrega(entregaDto);
    }

    @PatchMapping("/{entregaId}/concluir")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void concluirEntrega(@Valid @PathVariable Integer entregaId) {
        entregaService.concluirEntrega(entregaId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EntregaModel> findAllEntregas() {
        return entregaService.findAllEntregas();
    }

    @GetMapping("/page/{page}/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public Page<EntregaProjection> findAllEntregasPage(@PathVariable Integer page, @PathVariable Integer size) {
        return entregaService.findAllEntregasPage(page, size);
    }
}
