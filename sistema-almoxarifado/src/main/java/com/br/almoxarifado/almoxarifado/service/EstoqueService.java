package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.database.model.*;
import com.br.almoxarifado.almoxarifado.database.repository.*;
import com.br.almoxarifado.almoxarifado.dto.*;
import com.br.almoxarifado.almoxarifado.enums.EntradaEstoqueStatusEnum;
import com.br.almoxarifado.almoxarifado.enums.NotificationTypeEnum;
import com.br.almoxarifado.almoxarifado.exception.BadRequestException;
import com.br.almoxarifado.almoxarifado.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstoqueService {
    private final IEntradaEstoqueRepository entradaEstoqueRepository;
    private final ISaidaEstoqueRepository saidaEstoqueRepository;
    private final IItemTransporteRepository itemTransporteRepository;
    private final IItemRepository itemRepository;

    private final EmpresaService empresaService;
    private final CorredorService corredorService;
    private final ItemService itemService;
    private final NotificationService notificationService;

    @Transactional(rollbackOn = Exception.class)
    public void createEntradaEstoque(EntradaEstoqueDto entradaEstoqueDto) {
        EmpresaModel fornecedor = empresaService.findByNome(entradaEstoqueDto.getFornecedor());
        List<ItemTransporteModel> itens = new ArrayList<>();

        for(ItemMovimentacaoDto itemDto : entradaEstoqueDto.getItens()) {
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

        notificationService.sendNotification(NotificationDto.builder()
                .titulo("CADASTRO")
                .mensagem(String.format("Nova entrada de estoque de %s para %s.", fornecedor.getNome(), entradaEstoqueDto.getPrevisaoEntrega()))
                .type(NotificationTypeEnum.INFORMATIVA.name())
                .lido(false)
                .dataEmissao(LocalDate.now())
                .build()
        );
    }

    @Transactional(rollbackOn = Exception.class)
    public void concluirEntradaEstoque(Integer id) {
        EntradaEstoqueModel entrada =  entradaEstoqueRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entrada de estoque não encontrada!"));

        if(entrada.getStatus().equals(EntradaEstoqueStatusEnum.RECEBIDA)) {
            throw new BadRequestException("Entrada já concluida!");
        }

        for(ItemTransporteModel entradaItem : entrada.getItens()) {
            ItemModel item = itemRepository.findById(entradaItem.getItem().getId())
                    .orElseThrow(() -> new NotFoundException("Item não encontrado!"));

            Integer quantidadeRecebida = entradaItem.getQuantidade();
            itemService.addQuantidade(item, quantidadeRecebida);
            itemRepository.save(item);

            corredorService.addItemReceptaculo(entradaItem.getItem().getCategoria(), entradaItem.getItem(), quantidadeRecebida);
        }

        entrada.setStatus(EntradaEstoqueStatusEnum.RECEBIDA);
        entradaEstoqueRepository.save(entrada);
    }

    @Transactional(rollbackOn = Exception.class)
    public void createSaidaEstoque(SaidaEstoqueDto saidaEstoqueDto) {
        EmpresaModel cliente = empresaService.findByNome(saidaEstoqueDto.getCliente());
        List<ItemTransporteModel> itens = new ArrayList<>();

        for(ItemMovimentacaoDto itemDto : saidaEstoqueDto.getItens()) {
            ItemModel item = itemRepository.findByNome(itemDto.getNome())
                    .orElseThrow(() -> new NotFoundException("Item não encontrado!"));

            ItemTransporteModel saidaItem = ItemTransporteModel.builder()
                    .item(item)
                    .quantidade(itemDto.getQuantidade())
                    .build();

            Integer quantidadeEnviada = saidaItem.getQuantidade();
            itemService.subtractQuantidade(item, quantidadeEnviada);
            itemRepository.save(item);

            corredorService.removeItemReceptaculo(saidaItem.getItem().getCategoria(), saidaItem.getItem(), quantidadeEnviada);

            itemTransporteRepository.save(saidaItem);
            itens.add(saidaItem);
        }
        SaidaEstoqueModel entrada = new SaidaEstoqueModel(saidaEstoqueDto, cliente, itens);
        saidaEstoqueRepository.save(entrada);
    }

    public StatisticsDto getTodayStatistics() {
        Integer itensEmBaixa = itemRepository.countByQuantidadeLessThan(50);
        LocalDate hoje = LocalDate.now();

        Integer entradasRecentes = entradaEstoqueRepository.countByDataRegistroEquals(hoje);
        Integer saidasRecentes = saidaEstoqueRepository.countByDataRegistroEquals(hoje);

        return StatisticsDto.builder()
                .itensEmBaixa(itensEmBaixa)
                .entradasRecentes(entradasRecentes)
                .saidasRecentes(saidasRecentes)
                .build();
    }
}
