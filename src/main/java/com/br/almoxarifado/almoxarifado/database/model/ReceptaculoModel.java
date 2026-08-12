package com.br.almoxarifado.almoxarifado.database.model;

import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.*;

@Entity
@Table(name = "receptaculos")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReceptaculoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private static Integer capacidade = 50;
    private Integer emUso;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemModel item;

    public Integer getCapacidade() {
        return capacidade;
    }

    public Integer getCapacidadeDisponivel() {

        return capacidade - emUso;
    }

    public void addQuantidade(Integer quantidade) {
        this.emUso += quantidade;
    }

    public ReceptaculoModel() {
        this.emUso = 0;
    }
}
