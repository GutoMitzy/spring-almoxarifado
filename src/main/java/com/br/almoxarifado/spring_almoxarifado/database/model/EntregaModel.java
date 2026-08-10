package com.br.almoxarifado.spring_almoxarifado.database.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private Set<PecaModel> pecas = new HashSet<>();
}
