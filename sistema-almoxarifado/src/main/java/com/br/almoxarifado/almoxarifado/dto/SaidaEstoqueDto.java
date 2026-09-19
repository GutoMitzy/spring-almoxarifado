package com.br.almoxarifado.almoxarifado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record SaidaEstoqueDto (@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate previsaoEntrega,
                               @NotBlank String cliente,
                               @NotNull List<ItemMovimentacaoDto> itens){

}
