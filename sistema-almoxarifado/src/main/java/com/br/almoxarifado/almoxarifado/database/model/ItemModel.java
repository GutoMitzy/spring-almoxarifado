package com.br.almoxarifado.almoxarifado.database.model;

import com.br.almoxarifado.almoxarifado.dto.ItemDto;
import com.br.almoxarifado.almoxarifado.enums.ItemStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nome;
    private String descricao;
    @Column(nullable = false)
    private Integer quantidade = 0;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemStatusEnum status = ItemStatusEnum.ESGOTADO;
    @Column(nullable = false)
    private BigDecimal precoUnitario;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaModel categoria;

    public ItemModel(ItemDto data, CategoriaModel categoria) {
        this.nome = data.nome();
        this.descricao = data.descricao();
        this.categoria = categoria;
        this.precoUnitario = data.precoUnitario();
    }

}
