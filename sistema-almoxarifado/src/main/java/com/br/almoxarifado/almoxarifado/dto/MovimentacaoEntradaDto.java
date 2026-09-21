package com.br.almoxarifado.almoxarifado.dto;

import com.br.almoxarifado.almoxarifado.database.model.EntradaEstoqueModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MovimentacaoEntradaDto(@NotNull Integer id,
                                     @NotBlank String status,
                                     @NotNull BigDecimal valorTotal,
                                     @NotBlank String fornecedor,
                                     @DateTimeFormat LocalDate dataPrevisao
                                     ) {

    public static MovimentacaoEntradaDto toDto(EntradaEstoqueModel model) {
        return new MovimentacaoEntradaDto(
                model.getId(), model.getStatus().name(), model.getValorTotal(), model.getEmpresa().getNome(), model.getDataPrevisao()
        );
    }
}
