package com.br.almoxarifado.almoxarifado.database.model;

import com.br.almoxarifado.almoxarifado.dto.NotificationDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "notificacoes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String mensagem;
    @Column(nullable = false)
    private String type;
    private Boolean lido;
    @Column(nullable = false)
    private LocalDate dataEmissao;

    public NotificationModel (NotificationDto data) {
        this.titulo = data.titulo();
        this.mensagem = data.mensagem();
        this.type = data.type();
        this.lido = data.lido();
        this.dataEmissao = data.dataEmissao();
    }
}
