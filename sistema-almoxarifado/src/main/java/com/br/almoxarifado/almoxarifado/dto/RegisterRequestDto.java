package com.br.almoxarifado.almoxarifado.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    private String telefone;
    @NotBlank
    private String endereco;
    @NotBlank
    private String tipo;
    @NotBlank
    private String senha;
}
