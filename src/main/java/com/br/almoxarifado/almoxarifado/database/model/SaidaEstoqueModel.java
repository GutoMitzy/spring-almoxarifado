package com.br.almoxarifado.almoxarifado.database.model;

import com.br.almoxarifado.almoxarifado.dto.EntradaEstoqueDto;
import com.br.almoxarifado.almoxarifado.dto.SaidaEstoqueDto;
import com.br.almoxarifado.almoxarifado.enums.EntradaEstoqueStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    private LocalDate data;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @ManyToOne
    @JoinColumn(name = "cliente")
    private EmpresaModel cliente;

    @OneToMany
    @JoinColumn(name = "item_transporte_saida_id")
    private List<ItemTransporteModel> itens;

    public SaidaEstoqueModel(SaidaEstoqueDto data, EmpresaModel cliente, List<ItemTransporteModel> itens) {
        this.data = LocalDate.now();
        this.valorTotal = data.getValorTotal();
        this.cliente = cliente;
        this.itens = itens;
    }
}
