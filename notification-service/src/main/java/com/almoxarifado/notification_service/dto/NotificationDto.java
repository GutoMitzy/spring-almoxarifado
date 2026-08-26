package com.almoxarifado.notification_service.dto;

import jakarta.validation.constraints.NotBlank;

public record NotificationDto(@NotBlank String titulo, @NotBlank String mensagem) {
}
