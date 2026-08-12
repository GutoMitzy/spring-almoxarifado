package com.br.almoxarifado.almoxarifado.controller;

import com.br.almoxarifado.almoxarifado.dto.ItemProjection;
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
    public Page<ItemProjection> findAllItemsPage(@RequestParam Integer page, @RequestParam Integer size) {
        return itemService.findAllItemsPage(page, size);
    }

    @GetMapping("/{categoria}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ItemProjection> findItemsByCategoria(@PathVariable String categoria, @RequestParam Integer page, @RequestParam Integer size) {
        return itemService.findItemsByCategoria(categoria, page, size);
    }
}
