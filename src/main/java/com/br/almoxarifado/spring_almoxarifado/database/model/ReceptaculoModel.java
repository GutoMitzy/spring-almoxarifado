package com.br.almoxarifado.spring_almoxarifado.database.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.EmbeddedColumnNaming;

import java.util.List;

@Table(name="receptaculos")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReceptaculoModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "receptaculo_id")
    private Integer id;

    private static final Integer CAPACIDADE = 50;

    @Column(nullable = false)
    private Integer quantidadeAtual = 0;

    @ManyToOne
    @JoinColumn(name= "peca_id")
    private PecaModel peca;

    public int getCapacidadeDisponivel() {
        return CAPACIDADE - quantidadeAtual;
    }

    public Integer getCapacidade() {
        return CAPACIDADE;
    }
}
