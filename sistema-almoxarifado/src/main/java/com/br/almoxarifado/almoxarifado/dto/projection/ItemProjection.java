package com.br.almoxarifado.almoxarifado.dto.projection;

public interface ItemProjection {
    Integer getItemId();
    String getNome();
    String getDescricao();
    Integer getQuantidade();
    String getStatus();
    Integer getCategoriaId();
    String getCategoriaNome();
    String getCategoriaDescricao();
}
