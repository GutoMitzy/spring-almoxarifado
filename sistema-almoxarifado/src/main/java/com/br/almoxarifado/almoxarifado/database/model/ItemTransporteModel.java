package com.br.almoxarifado.almoxarifado.database.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transporte_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemTransporteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemModel item;
}
