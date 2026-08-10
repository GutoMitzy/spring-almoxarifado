package com.br.almoxarifado.spring_almoxarifado.database.model;

import jakarta.persistence.*;
import lombok.*;

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

    private static Integer tamanho = 50;

    @OneToOne
    @JoinTable(
            name = "receptaculos_corredores",
            joinColumns = @JoinColumn(name = "receptaculo_id"),
            inverseJoinColumns = @JoinColumn(name = "corredor_id")
    )
    private CorredorModel corredor;
}
