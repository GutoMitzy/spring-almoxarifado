package com.br.almoxarifado.almoxarifado.database.repository;

import com.br.almoxarifado.almoxarifado.database.model.CategoriaModel;
import com.br.almoxarifado.almoxarifado.database.model.CorredorModel;
import com.br.almoxarifado.almoxarifado.dto.projection.CorredorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface ICorredorRepository extends JpaRepository<CorredorModel, Integer> {
    List<CorredorModel> findByCategoria(CategoriaModel categoria);


    @NativeQuery(value = """
        SELECT  c.id                corredorId,
                c.categoria_id      categoriaId,
                ct.nome             categoriaNome,
                ct.descricao        categoriaDescricao,
                r.id				receptaculoId,
                r.em_uso            receptaculoUso,
                r.item_id           itemId,
                i.nome              itemNome,
                i.descricao         itemDescricao
        FROM corredores c
        INNER JOIN categorias ct
            ON c.categoria_id = ct.id
        INNER JOIN receptaculos r
            ON r.corredor_id = c.id
        INNER JOIN itens i
            ON r.item_id = i.id
        WHERE c.id = :id
        ORDER BY r.id;
    """, countQuery = """
            SELECT COUNT(*)
            FROM corredores c
            INNER JOIN categorias ct
                ON c.categoria_id = ct.id
            INNER JOIN receptaculos r
                ON r.corredor_id = c.id
            INNER JOIN itens i
                ON r.item_id = i.id;
            WHERE c.id = :id
            """)
    Page<CorredorProjection> findCorredorByIdPage(@Param("id") Integer id, Pageable pageable);

    @NativeQuery(value = """
    SELECT  c.id                    corredorId,
            ct.nome                 categoriaNome,
            ct.descricao            categoriaDescricao,
            r.id                    receptaculoId,
            r.em_uso                receptaculoUso,
            i.id                    itemId,
            i.nome                  itemNome,
            i.descricao             itemDescricao
        FROM corredores c
        INNER JOIN categorias ct
            ON ct.id = c.categoria_id
        LEFT JOIN receptaculos r
            ON r.corredor_id = c.id
        LEFT JOIN itens i
            ON i.id = r.item_id
        LEFT JOIN estoques e
            ON e.peca_id = r.id
        ORDER BY c.id, r.id
    """)
    List<CorredorProjection> findCorredoresInfo();
}
