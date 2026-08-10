package com.br.almoxarifado.spring_almoxarifado.dto;

import com.br.almoxarifado.spring_almoxarifado.database.model.PecaModel;

import java.time.LocalDate;
import java.util.Set;

public interface EntregaProjection {
    Integer getEntregaId();

    String getFornecedor();

    LocalDate getDataEntrega();

    Boolean getEntregue();

    Integer getPecaId();

    String getDescricao();

    String getNome();

    Integer getEstoque();
}
