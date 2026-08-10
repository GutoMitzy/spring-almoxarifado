package com.br.almoxarifado.spring_almoxarifado.database.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pecas")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PecaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "peca_id")
    private Integer id;

    private String nome;
    private String descricao;
    private Integer estoque;

    @OneToOne
    @JoinTable(
            name = "pecas_receptaculos",
            joinColumns = @JoinColumn(name = "peca_id"),
            inverseJoinColumns = @JoinColumn(name = "receptaculo_id")
    )
    private ReceptaculoModel receptaculo;
}
