package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.database.model.CategoriaModel;
import com.br.almoxarifado.almoxarifado.database.model.ItemModel;
import com.br.almoxarifado.almoxarifado.database.repository.IItemRepository;
import com.br.almoxarifado.almoxarifado.dto.ItemDto;
import com.br.almoxarifado.almoxarifado.exception.NotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    IItemRepository itemRepository;

    @Mock
    CategoriaService categoriaService;

    @Mock
    EstoqueService estoqueService;

    @InjectMocks
    private ItemService itemService;

    @Test
    @DisplayName("Should create Item successfully when everything is OK")
    void createItemSuccess() {
        ItemDto itemDto = new ItemDto("resistores", "", "eletronica");
        CategoriaModel categoria = new CategoriaModel("eletronica", "");

        when(categoriaService.findByNome(categoria.getNome())).thenReturn(categoria);

        itemService.createItem(itemDto);

        ArgumentCaptor<ItemModel> item = ArgumentCaptor.forClass(ItemModel.class);

        verify(categoriaService, times(1)).findByNome("eletronica");
        verify(estoqueService, times(1)).createEstoque(any(ItemModel.class));
        verify(itemRepository).save(item.capture());

        ItemModel savedItem = item.getValue();

        assertEquals("resistores", savedItem.getNome());
        assertEquals("eletronica", savedItem.getCategoria().getNome());
    }

    @Test
    @DisplayName("Should not create Item when Categoria not found")
    void createItemError() {
        String categoria = "eletronica";
        ItemDto itemDto = new ItemDto("resistores", "", categoria);

        when(categoriaService.findByNome(categoria)).thenThrow(new NotFoundException("Categoria não encontrada!"));

        assertThrows(NotFoundException.class, () -> {
                itemService.createItem(itemDto);
        });

        verify(categoriaService, times(1)).findByNome(categoria);
        verify(estoqueService, never()).createEstoque(any());
        verify(itemRepository, never()).save(any());
    }

}