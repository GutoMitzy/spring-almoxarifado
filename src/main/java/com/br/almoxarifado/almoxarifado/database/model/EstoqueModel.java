package com.br.almoxarifado.almoxarifado.database.model;

import com.br.almoxarifado.almoxarifado.exception.BadRequestException;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estoques")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantidade=0;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "peca_id")
    private ItemModel item;

    public void addQuantidade(Integer quantidade) {
        this.quantidade += quantidade;
    }

    public void subtractQuantidade(Integer quantidade) {
        if(quantidade > this.quantidade) {
            throw new BadRequestException("Não há estoque suficiente para a saída!");
        }

        this.quantidade -= quantidade;
    }
}
