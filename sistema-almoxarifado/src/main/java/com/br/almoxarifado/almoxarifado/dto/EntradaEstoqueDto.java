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
public class EntradaEstoqueDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @NotNull
    private BigDecimal valorTotal;

    @NotBlank
    private String fornecedor;

    @NotNull
    private List<EntradaItemDto> itens;

}
