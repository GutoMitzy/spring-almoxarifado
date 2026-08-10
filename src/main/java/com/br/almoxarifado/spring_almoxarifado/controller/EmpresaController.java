package com.br.almoxarifado.spring_almoxarifado.controller;

import com.br.almoxarifado.spring_almoxarifado.database.model.EmpresaModel;
import com.br.almoxarifado.spring_almoxarifado.dto.EmpresaDto;
import com.br.almoxarifado.spring_almoxarifado.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/almoxarifado/empresas")
@RequiredArgsConstructor
@Validated
public class EmpresaController {
    private final EmpresaService empresaService;

    @GetMapping("/{empresaId}")
    @ResponseStatus(HttpStatus.OK)
    public EmpresaModel getEmpresa(@PathVariable Integer empresaId) {
        return empresaService.getEmpresaById(empresaId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmpresaModel> getAllEmpresas() {
        return empresaService.getAllEmpresas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEmpresa(@Valid @RequestBody EmpresaDto empresaDto) {
        empresaService.saveEmpresa(empresaDto);
    }
}
