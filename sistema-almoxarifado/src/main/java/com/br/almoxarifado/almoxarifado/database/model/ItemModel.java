package com.br.almoxarifado.almoxarifado.database.model;

import com.br.almoxarifado.almoxarifado.dto.ItemDto;
import com.br.almoxarifado.almoxarifado.enums.ItemStatusEnum;
import com.br.almoxarifado.almoxarifado.exception.BadRequestException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

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
    private String status = ItemStatusEnum.ESGOTADO.name();
    @Column(nullable = false)
    private BigDecimal precoUnitario;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaModel categoria;

    public ItemModel(ItemDto data, CategoriaModel categoria) {
        this.nome = data.getNome();
        this.descricao = data.getDescricao();
        this.categoria = categoria;
        this.precoUnitario = data.getPrecoUnitario();
    }

    public void addQuantidade(Integer quantidade) {
        this.quantidade += quantidade;
        updateStatus();
    }

    public void subtractQuantidade(Integer quantidade) {
        if(quantidade > this.quantidade) {
            throw new BadRequestException("Não há estoque suficiente para a saída!");
        }

        this.quantidade -= quantidade;
        updateStatus();
    }

    public void updateStatus() {
        if(this.quantidade >= 50) {
            this.status = ItemStatusEnum.DISPONIVEL.name();
        } else if(this.quantidade < 50) {
            this.status = ItemStatusEnum.BAIXO_ESTOQUE.name();
        } else {
            this.status = ItemStatusEnum.ESGOTADO.name();
        }
    }
}
