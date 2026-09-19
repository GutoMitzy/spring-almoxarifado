package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.database.model.CategoriaModel;
import com.br.almoxarifado.almoxarifado.database.model.ItemModel;
import com.br.almoxarifado.almoxarifado.database.repository.IItemRepository;
import com.br.almoxarifado.almoxarifado.dto.ItemDto;
import com.br.almoxarifado.almoxarifado.dto.NotificationDto;
import com.br.almoxarifado.almoxarifado.enums.ItemStatusEnum;
import com.br.almoxarifado.almoxarifado.enums.NotificationTypeEnum;
import com.br.almoxarifado.almoxarifado.exception.BadRequestException;
import com.br.almoxarifado.almoxarifado.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final IItemRepository itemRepository;

    private final CategoriaService categoriaService;
    private final NotificationService notificationService;

    @Transactional(rollbackOn =  Exception.class)
    public void createItem(ItemDto data) {
        CategoriaModel categoria = categoriaService.findByNome(data.categoria());
        ItemModel item = new ItemModel(data, categoria);

        itemRepository.save(item);
    }

    public void addQuantidade(ItemModel item, Integer quantidade) {
        item.setQuantidade(item.getQuantidade() + quantidade);
        updateStatus(item);
    }

    public void subtractQuantidade(ItemModel item, Integer quantidade) {
        if(quantidade > item.getQuantidade()) {
            throw new BadRequestException("Não há estoque suficiente para a saída!");
        }

        item.setQuantidade(item.getQuantidade() - quantidade);
        updateStatus(item);
    }

    public void updateStatus(ItemModel item) {
        ItemStatusEnum currStatus = item.getStatus();
        ItemStatusEnum newStatus;

        if(item.getQuantidade() >= 50) {
            newStatus = ItemStatusEnum.DISPONIVEL;
        } else if(item.getQuantidade() >= 1) {
            newStatus = ItemStatusEnum.BAIXO_ESTOQUE;
        } else {
            newStatus = ItemStatusEnum.ESGOTADO;
        }

        if (!newStatus.equals(currStatus)) {
            item.setStatus(newStatus);

            String messageFormat = switch (newStatus) {
                case DISPONIVEL -> "atingiu zero.";
                case BAIXO_ESTOQUE -> "abaixo do estoque mínimo.";
                case ESGOTADO -> "abastecido.";
            };

            String type = switch(newStatus) {
                case DISPONIVEL, BAIXO_ESTOQUE -> NotificationTypeEnum.ATENÇÃO.name();
                case ESGOTADO -> NotificationTypeEnum.URGENTE.name();
            };

            notificationService.sendNotification(NotificationDto.builder()
                            .titulo(newStatus.name())
                            .mensagem(String.format("Estoque de %s %s", item.getNome(), messageFormat))
                            .type(type)
                            .lido(false)
                            .dataEmissao(LocalDate.now())
                    .build()
            );
        }
    }

    public Page<ItemDto> findAllItemsPage(Integer page, Integer size) {
        return itemRepository.findAll(PageRequest.of(page, size)).map(ItemDto::toDto);
    }

    public Page<ItemDto> findItemsByCategoria(String categoria, Integer page, Integer size) {
        return itemRepository.findByCategoriaNome(categoria, PageRequest.of(page, size)).map(ItemDto::toDto);
    }

    public ItemModel findByNome(String nome) {
        return itemRepository.findByNome(nome)
                .orElseThrow(() -> new NotFoundException("Item não encontrado!"));
    }

    public Page<ItemDto> findItemsByQuantidade(Integer quantidade, Integer minimo, Integer page, Integer size) {
        return itemRepository.findByQuantidadeLessThanEqualAndQuantidadeGreaterThanEqual(quantidade, minimo, PageRequest.of(page, size)).map(ItemDto::toDto);
    }
}
