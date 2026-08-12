package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.database.model.EstoqueModel;
import com.br.almoxarifado.almoxarifado.database.model.ItemModel;
import com.br.almoxarifado.almoxarifado.database.repository.IEstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstoqueService {
    private final IEstoqueRepository estoqueRepository;

    public void createEstoque(ItemModel data) {
        estoqueRepository.save(EstoqueModel.builder()
                        .quantidade(0)
                        .item(data)
                .build());
    }
}
