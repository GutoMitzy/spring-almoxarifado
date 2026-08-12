package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.database.model.*;
import com.br.almoxarifado.almoxarifado.database.repository.IEntradaEstoqueRepository;
import com.br.almoxarifado.almoxarifado.database.repository.IEntradaItemRepository;
import com.br.almoxarifado.almoxarifado.database.repository.IEstoqueRepository;
import com.br.almoxarifado.almoxarifado.dto.EntradaEstoqueDto;
import com.br.almoxarifado.almoxarifado.dto.EntradaItemDto;
import com.br.almoxarifado.almoxarifado.enums.EntradaEstoqueStatusEnum;
import com.br.almoxarifado.almoxarifado.exception.BadRequestException;
import com.br.almoxarifado.almoxarifado.exception.EstoqueCheioException;
import com.br.almoxarifado.almoxarifado.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntradaEstoqueService {
    private final IEntradaEstoqueRepository entradaEstoqueRepository;
    private final IEntradaItemRepository entradaItemRepository;
    private final IEstoqueRepository estoqueRepository;

    private final EmpresaService empresaService;
    private final ItemService itemService;
    private final CorredorService corredorService;

    @Transactional(rollbackOn = Exception.class)
    public void createEntradaEstoque(EntradaEstoqueDto entradaEstoqueDto) {
        EmpresaModel fornecedor = empresaService.findByNome(entradaEstoqueDto.getFornecedor());
        List<EntradaItemModel> itens = new ArrayList<>();

        for(EntradaItemDto itemDto : entradaEstoqueDto.getItens()) {
            ItemModel item = itemService.findByNome(itemDto.getNome());
            EntradaItemModel entradaItem = EntradaItemModel.builder()
                    .item(item)
                    .quantidade(itemDto.getQuantidade())
                    .build();

            entradaItemRepository.save(entradaItem);

            itens.add(entradaItem);
        }
        EntradaEstoqueModel entrada = new EntradaEstoqueModel(entradaEstoqueDto, fornecedor, itens);
        entradaEstoqueRepository.save(entrada);
    }

    @Transactional(rollbackOn = Exception.class)
    public void concluirEntradaEstoque(Integer id) {
        EntradaEstoqueModel entrada =  entradaEstoqueRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entrada de estoque não encontrada!"));

        if(entrada.getStatus().equals(EntradaEstoqueStatusEnum.RECEBIDA)) {
            throw new BadRequestException("Entrada já concluida!");
        }

        for(EntradaItemModel entradaItem : entrada.getItens()) {
            EstoqueModel estoque = estoqueRepository.findById(entradaItem.getItem().getId())
                    .orElseThrow(() -> new NotFoundException("Estoque não encontrado!"));

            Integer quantidadeRecebida = entradaItem.getQuantidade();
            estoque.addQuantidade(quantidadeRecebida);
            estoqueRepository.save(estoque);

            corredorService.addItemReceptaculo(entradaItem.getItem().getCategoria(), entradaItem.getItem(), quantidadeRecebida);
        }

        entrada.setStatus(EntradaEstoqueStatusEnum.RECEBIDA);
        entradaEstoqueRepository.save(entrada);
    }
}
