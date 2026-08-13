package com.br.almoxarifado.almoxarifado.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "corredores")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CorredorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaModel categoria;

    @OneToMany
    @JoinColumn(name = "corredor_id")
    private List<ReceptaculoModel> receptaculos;


}
