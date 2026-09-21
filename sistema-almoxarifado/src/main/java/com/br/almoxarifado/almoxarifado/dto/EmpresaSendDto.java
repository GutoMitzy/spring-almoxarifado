package com.br.almoxarifado.almoxarifado.dto;

import com.br.almoxarifado.almoxarifado.database.model.EmpresaModel;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.BooleanFlag;
import lombok.Builder;

@Builder
public record EmpresaSendDto(@NotBlank String nome,
                             @NotBlank String email,
                             @NotBlank String telefone,
                             @NotBlank String tipo,
                             @NotBlank String segmento,
                             @NotBlank String cidade,
                             @NotBlank String uf,
                             @NotBlank String ddd,
                             @BooleanFlag boolean ativo){


    public static EmpresaSendDto toDto(EmpresaModel empresa) {
        return new EmpresaSendDto(
                empresa.getNome(), empresa.getEmail(), empresa.getTelefone(), empresa.getTipo(), empresa.getSegmento(), empresa.getCidade(), empresa.getUf(), empresa.getDdd(), empresa.isAtivo()
        );
    }
}
