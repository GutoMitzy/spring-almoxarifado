package com.br.almoxarifado.almoxarifado.database.model;

import com.br.almoxarifado.almoxarifado.dto.SaidaEstoqueDto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "saidas_estoque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaidaEstoqueModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate dataRegistro;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @ManyToOne
    @JoinColumn(name = "cliente")
    private EmpresaModel empresa;

    @OneToMany
    @JoinColumn(name = "item_transporte_saida_id")
    private List<ItemTransporteModel> itens;

    public SaidaEstoqueModel(SaidaEstoqueDto data, EmpresaModel cliente, List<ItemTransporteModel> itens) {
        this.valorTotal = itens.stream()
                .map(item -> item.getItem().getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.empresa = cliente;
        this.itens = itens;
    }
}
