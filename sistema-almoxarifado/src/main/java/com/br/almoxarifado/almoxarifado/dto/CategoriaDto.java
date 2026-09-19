package com.br.almoxarifado.almoxarifado.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
public record CategoriaDto (@NotBlank String nome,
                           String descricao){

}
