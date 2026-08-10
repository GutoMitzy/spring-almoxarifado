package com.br.almoxarifado.spring_almoxarifado.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoginRequestDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String senha;
}
