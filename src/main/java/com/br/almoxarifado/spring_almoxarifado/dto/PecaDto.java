package com.br.almoxarifado.spring_almoxarifado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PecaDto {
    @NotBlank
    private String nome;

    private String descricao;

    @NotNull
    private Integer estoque;
}
