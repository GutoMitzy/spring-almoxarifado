package com.br.almoxarifado.almoxarifado.database.repository;

import com.br.almoxarifado.almoxarifado.database.model.ItemModel;
import com.br.almoxarifado.almoxarifado.dto.ItemProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IItemRepository extends JpaRepository<ItemModel, Integer> {
    @NativeQuery(value = """
    SELECT  i.id                 itemId,
            i.nome               nome,
            i.descricao          descricao,
            i.categoria_id       categoriaId,
            c.nome               categoriaNome,
            c.descricao          categoriaDescricao
    FROM itens i
    INNER JOIN categorias c
    WHERE c.id = i.id
    """, countQuery = """
            SELECT COUNT(*)
            FROM itens i
            INNER JOIN categorias c
            WHERE c.id = i.id
    """)
    Page<ItemProjection> findAllItemsPage(Pageable pageable);


    @NativeQuery(value = """
    SELECT  i.id                 itemId,
            i.nome               nome,
            i.descricao          descricao,
            i.categoria_id       categoriaId,
            c.nome               categoriaNome,
            c.descricao          categoriaDescricao
    FROM itens i
    INNER JOIN categorias c
        ON c.id = i.id
    WHERE c.nome = :categoria
    """, countQuery = """
            SELECT COUNT(*)
            FROM itens i
            INNER JOIN categorias c
            ON c.id = i.id
            WHERE c.nome = :categoria
    """)
    Page<ItemProjection> findItemsByCategoria(String categoria, Pageable pageable);

    Optional<ItemModel> findByNome(String nome);
}
