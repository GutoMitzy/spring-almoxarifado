package com.br.almoxarifado.almoxarifado.controller;

import com.br.almoxarifado.almoxarifado.dto.EmpresaSendDto;
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
    public Page<EmpresaSendDto> findAllPage(@RequestParam(required = false) String nome,
                                            @RequestParam Integer page,
                                            @RequestParam Integer size) {
        return empresaService.findByNomePage(nome, page, size);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmpresaSendDto> findAllPage(@RequestParam(required = false, defaultValue = "0") Integer page,
                                              @RequestParam(required = false, defaultValue = "10") Integer size) {
        return empresaService.findAllPage(page, size);
    }


}
