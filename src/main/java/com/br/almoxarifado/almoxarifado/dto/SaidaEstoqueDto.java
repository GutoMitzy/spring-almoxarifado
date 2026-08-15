package com.br.almoxarifado.almoxarifado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaidaEstoqueDto {
    @NotNull
    private BigDecimal valorTotal;

    @NotBlank
    private String cliente;

    @NotNull
    private List<EntradaItemDto> itens;
}
