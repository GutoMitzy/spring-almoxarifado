package com.br.almoxarifado.spring_almoxarifado.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name="corredores")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CorredorModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "corredor_id")
    private Integer id;

    @OneToMany
    @JoinTable(
            name = "corredores_receptaculos",
            joinColumns = @JoinColumn(name = "corredor_id"),
            inverseJoinColumns = @JoinColumn(name = "receptaculo_id")
    )
    private List<ReceptaculoModel> receptaculos;

}
