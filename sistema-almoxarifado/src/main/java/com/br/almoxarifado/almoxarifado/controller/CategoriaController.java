package com.br.almoxarifado.almoxarifado.controller;

import com.br.almoxarifado.almoxarifado.database.model.CategoriaModel;
import com.br.almoxarifado.almoxarifado.dto.CategoriaDto;
import com.br.almoxarifado.almoxarifado.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/almoxarifado/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategoria(@Valid @RequestBody CategoriaDto data) {
        categoriaService.createCategoria(data);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CategoriaModel> findAllCategorias(@RequestParam Integer page, @RequestParam Integer size) {
        return categoriaService.findAllCategorias(page, size);
    }
}
