package com.br.almoxarifado.spring_almoxarifado.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntregaDto {
    @NotBlank
    private String fornecedor;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEntrega;

    private Set<Integer> pecas;

}
