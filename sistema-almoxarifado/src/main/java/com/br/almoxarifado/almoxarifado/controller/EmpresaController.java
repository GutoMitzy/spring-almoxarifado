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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<EmpresaDto> findAllPage(@RequestParam(required = false) String nome,
                                        @RequestParam Integer page,
                                        @RequestParam Integer size) {
        return empresaService.findByNomePage(nome, page, size);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmpresaDto> findAllPage(@RequestParam Integer page, @RequestParam Integer size) {
        return empresaService.findAllPage(page, size);
    }


}
