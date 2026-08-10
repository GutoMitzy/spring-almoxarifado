package com.br.almoxarifado.spring_almoxarifado.database.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "entregas")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntregaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String fornecedor;

    @Column(name = "data_entrega")
    private LocalDate dataEntrega;

    private Boolean entregue;

    @ManyToMany
    @JoinTable (
            name = "entregas_pecas",
            joinColumns = @JoinColumn (name = "entrega_id"),
            inverseJoinColumns = @JoinColumn (name = "peca_id")
    )
    private List<PecaModel> pecas = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "entregas_quantidades",
            joinColumns = @JoinColumn(name = "entrega_id")
    )
    @Column(name = "quantidade")
    @OrderColumn(name = "posicao")
    private List<Integer> quantidades;
}
