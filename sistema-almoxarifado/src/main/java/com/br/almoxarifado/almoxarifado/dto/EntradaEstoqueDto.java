package com.br.almoxarifado.almoxarifado.dto;

import com.br.almoxarifado.almoxarifado.database.model.EntradaEstoqueModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record EntradaEstoqueDto (@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate previsaoEntrega,
                                 @NotBlank String fornecedor,
                                 @NotNull List<ItemMovimentacaoDto> itens){

}
