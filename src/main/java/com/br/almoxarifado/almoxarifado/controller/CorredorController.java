package com.br.almoxarifado.almoxarifado.controller;

import com.br.almoxarifado.almoxarifado.database.model.CorredorModel;
import com.br.almoxarifado.almoxarifado.dto.CorredorDto;
import com.br.almoxarifado.almoxarifado.dto.CorredorProjection;
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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<CorredorProjection> findCorredorById(@PathVariable Integer id,
                                                     @RequestParam Integer page, @RequestParam Integer size) {
        return corredorService.findCorredorByIdPage(id, page, size);
    }
}
