package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.database.model.*;
import com.br.almoxarifado.almoxarifado.database.repository.*;
import com.br.almoxarifado.almoxarifado.dto.EntradaEstoqueDto;
import com.br.almoxarifado.almoxarifado.dto.EntradaItemDto;
import com.br.almoxarifado.almoxarifado.dto.SaidaEstoqueDto;
import com.br.almoxarifado.almoxarifado.enums.EntradaEstoqueStatusEnum;
import com.br.almoxarifado.almoxarifado.exception.BadRequestException;
import com.br.almoxarifado.almoxarifado.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstoqueService {
    private final IEstoqueRepository estoqueRepository;

    private final IEntradaEstoqueRepository entradaEstoqueRepository;
    private final ISaidaEstoqueRepository saidaEstoqueRepository;
    private final IItemTransporteRepository itemTransporteRepository;
    private final IItemRepository itemRepository;

    private final EmpresaService empresaService;
    private final CorredorService corredorService;

    public void createEstoque(ItemModel data) {
        estoqueRepository.save(EstoqueModel.builder()
                        .quantidade(0)
                        .item(data)
                .build());
    }

    @Transactional(rollbackOn = Exception.class)
    public void createEntradaEstoque(EntradaEstoqueDto entradaEstoqueDto) {
        EmpresaModel fornecedor = empresaService.findByNome(entradaEstoqueDto.getFornecedor());
        List<ItemTransporteModel> itens = new ArrayList<>();

        for(EntradaItemDto itemDto : entradaEstoqueDto.getItens()) {
            ItemModel item = itemRepository.findByNome(itemDto.getNome())
                    .orElseThrow(() -> new NotFoundException("Item não encontrado!"));
            ItemTransporteModel entradaItem = ItemTransporteModel.builder()
                    .item(item)
                    .quantidade(itemDto.getQuantidade())
                    .build();

            itemTransporteRepository.save(entradaItem);

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

        for(ItemTransporteModel entradaItem : entrada.getItens()) {
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

    @Transactional(rollbackOn = Exception.class)
    public void createSaidaEstoque(SaidaEstoqueDto saidaEstoqueDto) {
        EmpresaModel cliente = empresaService.findByNome(saidaEstoqueDto.getCliente());
        List<ItemTransporteModel> itens = new ArrayList<>();

        for(EntradaItemDto itemDto : saidaEstoqueDto.getItens()) {
            ItemModel item = itemRepository.findByNome(itemDto.getNome())
                    .orElseThrow(() -> new NotFoundException("Item não encontrado!"));
            ItemTransporteModel saidaItem = ItemTransporteModel.builder()
                    .item(item)
                    .quantidade(itemDto.getQuantidade())
                    .build();

            EstoqueModel estoque = estoqueRepository.findById(saidaItem.getItem().getId())
                    .orElseThrow(() -> new NotFoundException("Estoque não encontrado!"));

            Integer quantidadeEnviada = saidaItem.getQuantidade();
            estoque.subtractQuantidade(quantidadeEnviada);
            estoqueRepository.save(estoque);

            corredorService.removeItemReceptaculo(saidaItem.getItem().getCategoria(), saidaItem.getItem(), quantidadeEnviada);

            itemTransporteRepository.save(saidaItem);
            itens.add(saidaItem);
        }
        SaidaEstoqueModel entrada = new SaidaEstoqueModel(saidaEstoqueDto, cliente, itens);
        saidaEstoqueRepository.save(entrada);
    }
}
