package com.br.almoxarifado.spring_almoxarifado.controller;

import com.br.almoxarifado.spring_almoxarifado.database.model.CorredorModel;
import com.br.almoxarifado.spring_almoxarifado.database.repository.ICorredoresRepository;
import com.br.almoxarifado.spring_almoxarifado.dto.CorredorProjection;
import com.br.almoxarifado.spring_almoxarifado.service.CorredorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/almoxarifado/corredores")
@RequiredArgsConstructor
public class CorredorController {
    private final CorredorService corredorService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CorredorModel getCorredorById(@PathVariable Integer id) {
        return corredorService.findById(id);
    }

    @GetMapping("/{corredorId}/page/{page}/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public Page<CorredorProjection> getAllReceptaculosByCorredorPage(@PathVariable Integer corredorId, @PathVariable Integer page, @PathVariable Integer size) {
        return corredorService.findAllReceptaculosByCorredorPage(corredorId, page, size);
    }
}
