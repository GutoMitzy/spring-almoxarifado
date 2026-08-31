package com.br.almoxarifado.almoxarifado.controller;

import com.br.almoxarifado.almoxarifado.database.model.EmpresaModel;
import com.br.almoxarifado.almoxarifado.dto.EmpresaDto;
import com.br.almoxarifado.almoxarifado.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/almoxarifado/empresas")
@RequiredArgsConstructor
public class EmpresaController {
    private final EmpresaService empresaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEmpresa(@RequestBody EmpresaDto empresaDto) {
        empresaService.createEmpresa(empresaDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EmpresaModel findByNome(@RequestParam String nome, @RequestParam Integer page, @RequestParam Integer size) {
        return empresaService.findByNome(nome);
    }

}
