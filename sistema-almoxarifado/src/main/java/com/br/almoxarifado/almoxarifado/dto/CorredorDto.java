package com.br.almoxarifado.almoxarifado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CorredorDto {
    @NotBlank
    private String categoria;
    @NotNull
    private Integer receptaculos;
}
