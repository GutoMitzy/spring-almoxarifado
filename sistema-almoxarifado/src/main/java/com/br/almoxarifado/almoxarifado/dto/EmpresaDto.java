package com.br.almoxarifado.almoxarifado.dto;

import com.br.almoxarifado.almoxarifado.database.model.EmpresaModel;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaDto {
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

    public static EmpresaDto toDto(EmpresaModel empresa) {
        return new EmpresaDto(
                empresa.getNome(), empresa.getEmail(), empresa.getTelefone(), empresa.getEndereco(), empresa.getTipo()
        );
    }
}
