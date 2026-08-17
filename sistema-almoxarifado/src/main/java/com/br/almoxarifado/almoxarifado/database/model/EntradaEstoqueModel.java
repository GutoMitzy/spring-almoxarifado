package com.br.almoxarifado.almoxarifado.database.model;

import com.br.almoxarifado.almoxarifado.dto.EntradaEstoqueDto;
import com.br.almoxarifado.almoxarifado.enums.EntradaEstoqueStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "entradas_estoque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntradaEstoqueModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EntradaEstoqueStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "fornecedor")
    private EmpresaModel fornecedor;

    @OneToMany
    @JoinColumn(name = "item_transporte_entrada_id")
    private List<ItemTransporteModel> itens;

    public EntradaEstoqueModel(EntradaEstoqueDto data, EmpresaModel fornecedor, List<ItemTransporteModel> itens) {
        this.data = data.getData();
        this.valorTotal = data.getValorTotal();
        this.status = EntradaEstoqueStatusEnum.PENDENTE;
        this.fornecedor = fornecedor;
        this.itens = itens;
    }
}
