package com.br.almoxarifado.almoxarifado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
public record CorredorDto (@NotBlank String categoria,
                           @NotNull Integer receptaculos){

}
