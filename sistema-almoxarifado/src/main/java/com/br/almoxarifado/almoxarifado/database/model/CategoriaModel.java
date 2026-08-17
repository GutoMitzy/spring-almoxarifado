package com.br.almoxarifado.almoxarifado.database.model;

import com.br.almoxarifado.almoxarifado.dto.CategoriaDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String descricao;

    public CategoriaModel(CategoriaDto data) {
        this.nome = data.getNome();
        this.descricao = data.getDescricao();
    }

    public CategoriaModel(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}
