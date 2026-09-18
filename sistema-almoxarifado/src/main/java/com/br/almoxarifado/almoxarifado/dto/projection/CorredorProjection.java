package com.br.almoxarifado.almoxarifado.dto.projection;

public interface CorredorProjection {
    Long getCorredorId();

    String getCategoriaNome();

    String getSetorNome();

    String getCategoriaDescricao();

    Long getReceptaculoId();

    Integer getReceptaculoUso();

    Long getItemId();

    String getItemNome();

    String getItemDescricao();

    Integer getQuantidadeAtual();

    String getItemStatus();
}
