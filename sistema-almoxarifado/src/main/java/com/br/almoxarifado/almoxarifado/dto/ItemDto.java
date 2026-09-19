package com.br.almoxarifado.almoxarifado.dto;

import com.br.almoxarifado.almoxarifado.database.model.ItemModel;
import com.br.almoxarifado.almoxarifado.enums.ItemStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private ItemStatusEnum status;
    @NotNull
    private BigDecimal precoUnitario;

    public static ItemDto toDto(ItemModel item) {
        return new ItemDto(
                item.getNome(), item.getDescricao(), item.getCategoria().getNome(), item.getQuantidade(), item.getStatus(), item.getPrecoUnitario()
        );
    }
}
