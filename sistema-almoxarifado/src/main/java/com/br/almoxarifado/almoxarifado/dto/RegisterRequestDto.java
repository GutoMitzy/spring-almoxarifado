package com.br.almoxarifado.almoxarifado.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
public record RegisterRequestDto (@NotBlank String nome,
                                  @NotBlank String email,
                                  @NotBlank String telefone,
                                  @NotBlank String cidade,
                                  @NotBlank String uf,
                                  @NotBlank String ddd,
                                  @NotBlank String tipo,
                                  @NotBlank String senha){

}
