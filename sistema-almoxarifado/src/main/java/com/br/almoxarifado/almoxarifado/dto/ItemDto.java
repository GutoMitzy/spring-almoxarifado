package com.br.almoxarifado.almoxarifado.dto;

import com.br.almoxarifado.almoxarifado.database.model.ItemModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    @NotBlank
    private String nome;
    private String descricao;
    @NotBlank
    private String categoria;
    @NotNull
    private Integer quantidade;
    @NotNull
    private BigDecimal precoUnitario;

    public static ItemDto toDto(ItemModel item) {
        return new ItemDto(
                item.getNome(), item.getDescricao(), item.getCategoria().getNome(), item.getQuantidade(), item.getPrecoUnitario()
        );
    }
}
