package com.br.almoxarifado.almoxarifado.dto;

import com.br.almoxarifado.almoxarifado.database.model.NotificationModel;
import com.br.almoxarifado.almoxarifado.enums.NotificationTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    public static NotificationDto toDto(NotificationModel notification) {
        return new NotificationDto(
                notification.getTitulo(), notification.getMensagem(), notification.getType(), notification.getLido(), notification.getDataEmissao()
        );
    }
}
