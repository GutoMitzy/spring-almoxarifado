package com.br.almoxarifado.almoxarifado.dto;

import com.br.almoxarifado.almoxarifado.database.model.EmpresaModel;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
public record EmpresaDto (@NotBlank String nome,
                          @NotBlank String email,
                          @NotBlank String telefone,
                          @NotBlank String endereco,
                          @NotBlank String tipo,
                          @NotBlank String segmento){


    public static EmpresaDto toDto(EmpresaModel empresa) {
        return new EmpresaDto(
                empresa.getNome(), empresa.getEmail(), empresa.getTelefone(), empresa.getEndereco(), empresa.getTipo(), empresa.getSegmento()
        );
    }
}
