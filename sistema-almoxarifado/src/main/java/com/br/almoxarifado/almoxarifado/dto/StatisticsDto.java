package com.br.almoxarifado.almoxarifado.dto;

import lombok.Builder;

@Builder
public record StatisticsDto (Integer itensEmBaixa, Integer entradasRecentes, Integer saidasRecentes){
}
