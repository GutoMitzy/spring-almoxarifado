package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.database.model.CategoriaModel;
import com.br.almoxarifado.almoxarifado.database.model.ItemModel;
import com.br.almoxarifado.almoxarifado.database.repository.IItemRepository;
import com.br.almoxarifado.almoxarifado.dto.ItemProjection;
import com.br.almoxarifado.almoxarifado.dto.ItemDto;
import com.br.almoxarifado.almoxarifado.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final IItemRepository itemRepository;
    private final CategoriaService categoriaService;
    private final EstoqueService estoqueService;

    @Transactional(rollbackOn =  Exception.class)
    public void createItem(ItemDto data) {
        CategoriaModel categoria = categoriaService.findByNome(data.getCategoria());
        ItemModel item = new ItemModel(data, categoria);

        estoqueService.createEstoque(item);
        itemRepository.save(item);
    }

    public Page<ItemProjection> findAllItemsPage(Integer page, Integer size) {
        return itemRepository.findAllItemsPage(PageRequest.of(page, size));
    }

    public Page<ItemProjection> findItemsByCategoria(String categoria, Integer page, Integer size) {
        return itemRepository.findItemsByCategoria(categoria, PageRequest.of(page, size));
    }

    public ItemModel findByNome(String nome) {
        return itemRepository.findByNome(nome)
                .orElseThrow(() -> new NotFoundException("Item não encontrado!"));
    }
}
