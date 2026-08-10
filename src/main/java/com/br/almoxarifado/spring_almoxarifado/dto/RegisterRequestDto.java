package com.br.almoxarifado.spring_almoxarifado.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegisterRequestDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotBlank
    private String telefone;
    @NotBlank
    private String senha;
}
