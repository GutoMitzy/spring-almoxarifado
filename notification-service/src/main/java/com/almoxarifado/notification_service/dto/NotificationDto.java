package com.almoxarifado.notification_service.dto;

import jakarta.validation.constraints.NotBlank;
import jdk.jfr.BooleanFlag;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
public record NotificationDto(@NotBlank String titulo,
                              @NotBlank String mensagem,
                              @NotBlank String type,
                              @BooleanFlag Boolean lido,
                              @DateTimeFormat LocalDate dataEmissao
) {
}