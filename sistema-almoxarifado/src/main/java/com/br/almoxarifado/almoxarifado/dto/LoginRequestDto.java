package com.br.almoxarifado.almoxarifado.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
public record LoginRequestDto (@NotBlank String nome,
                               @NotBlank String senha){

}
