package com.br.almoxarifado.almoxarifado.dto;

public interface ItemProjection {
    Integer getItemId();
    String getNome();
    String getDescricao();
    Integer getCategoriaId();
    String getCategoriaNome();
    String getCategoriaDescricao();
}
