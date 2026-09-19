package com.br.almoxarifado.almoxarifado.controller;

import com.br.almoxarifado.almoxarifado.database.model.CorredorModel;
import com.br.almoxarifado.almoxarifado.dto.CorredorDto;
import com.br.almoxarifado.almoxarifado.service.CorredorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/almoxarifado/corredores")
@RequiredArgsConstructor
@Validated
public class CorredorController {
    private final CorredorService corredorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCorredor(@Valid @RequestBody CorredorDto corredorDto) {
        corredorService.createCorredor(corredorDto);

    }

    @GetMapping("/setores")
    @ResponseStatus(HttpStatus.OK)
    public Page<CorredorModel> findCorredorBySetorPage(@RequestParam String setor,
                                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size) {
        return corredorService.findCorredorBySetorPage(setor, page, size);
    }
}
