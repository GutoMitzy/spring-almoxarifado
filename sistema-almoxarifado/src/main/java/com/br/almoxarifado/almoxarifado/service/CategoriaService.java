package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.database.model.CategoriaModel;
import com.br.almoxarifado.almoxarifado.database.repository.ICategoriaRepository;
import com.br.almoxarifado.almoxarifado.dto.CategoriaDto;
import com.br.almoxarifado.almoxarifado.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    private final ICategoriaRepository categoriasRepository;

    public void createCategoria(CategoriaDto data) {
        categoriasRepository.save(new CategoriaModel(data));
    }

    public CategoriaModel findByNome(String nome) {
        return categoriasRepository.findByNome(nome)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada!"));
    }

    public Page<CategoriaModel> findAllCategorias(Integer page, Integer size) {
        return categoriasRepository.findAll(PageRequest.of(page, size));
    }
}
