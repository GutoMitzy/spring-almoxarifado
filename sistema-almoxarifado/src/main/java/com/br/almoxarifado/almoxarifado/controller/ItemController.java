package com.br.almoxarifado.almoxarifado.controller;

import com.br.almoxarifado.almoxarifado.dto.ItemDto;
import com.br.almoxarifado.almoxarifado.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/almoxarifado/itens")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createItem(@RequestBody ItemDto data) {
        itemService.createItem(data);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ItemDto> findAllItemsPage(@RequestParam(required = false, defaultValue = "0") Integer page,
                                          @RequestParam(required = false, defaultValue = "10") Integer size) {
        return itemService.findAllItemsPage(page, size);
    }

    @GetMapping("/categoria")
    @ResponseStatus(HttpStatus.OK)
    public Page<ItemDto> findItemsByCategoria(@RequestParam String categoria,
                                              @RequestParam(required = false, defaultValue = "0") Integer page,
                                              @RequestParam(required = false, defaultValue = "10") Integer size) {
        return itemService.findItemsByCategoria(categoria, page, size);
    }

    @GetMapping("/estoque")
    @ResponseStatus(HttpStatus.OK)
    public Page<ItemDto> findItemsByQuantidade(@RequestParam Integer quantidade,
                                               @RequestParam(required = false, defaultValue = "1") Integer minimo,
                                               @RequestParam(required = false, defaultValue = "0") Integer page,
                                               @RequestParam(required = false, defaultValue = "10") Integer size) {
        return itemService.findItemsByQuantidade(quantidade, minimo, page, size);
    }
}
