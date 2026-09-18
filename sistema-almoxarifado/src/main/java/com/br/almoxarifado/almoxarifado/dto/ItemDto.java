package com.br.almoxarifado.almoxarifado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
}
