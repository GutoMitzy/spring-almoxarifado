package com.br.almoxarifado.almoxarifado.dto;

import jakarta.validation.constraints.NotBlank;

public record NotificationDto(@NotBlank String titulo, @NotBlank String mensagem) {
}
