package com.br.almoxarifado.almoxarifado.database.repository;

import com.br.almoxarifado.almoxarifado.database.model.ItemModel;
import com.br.almoxarifado.almoxarifado.dto.projection.CategoriaContagemProjection;
import com.br.almoxarifado.almoxarifado.dto.projection.ItemProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IItemRepository extends JpaRepository<ItemModel, Integer> {
    @NativeQuery(value = """
    SELECT  i.id                 itemId,
            i.nome               nome,
            i.descricao          descricao,
            i.quantidade         quantidade,
            i.status             status,
            i.categoria_id       categoriaId,
            c.nome               categoriaNome,
            c.descricao          categoriaDescricao
    FROM itens i
    INNER JOIN categorias c
    WHERE c.id = i.categoria_id
    ORDER BY i.id
    """, countQuery = """
            SELECT COUNT(*)
            FROM itens i
            INNER JOIN categorias c
            WHERE c.id = i.categoria_id
    """)
    Page<ItemProjection> findAllPage(Pageable pageable);


    @NativeQuery(value = """
    SELECT  i.id                 itemId,
            i.nome               nome,
            i.descricao          descricao,
            i.categoria_id       categoriaId,
            i.quantidade         quantidade,
            i.status             status,
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
    Page<ItemProjection> findByCategoria(String categoria, Pageable pageable);

    @NativeQuery("""
        SELECT c.nome AS categoria, COUNT(i.id) AS quantidade
        FROM itens i
        INNER JOIN categorias c 
            ON c.id = i.categoria_id
        GROUP BY c.nome
        ORDER BY c.nome
    """)
    List<CategoriaContagemProjection> countByCategoria();

    Optional<ItemModel> findByNome(String nome);

    @NativeQuery(value = """
    SELECT  i.id                 itemId,
            i.nome               itemNome,
            i.descricao          descricao,
            i.quantidade         quantidade,
            i.status             status,
            c.nome				 categoriaNome,
            c.descricao			 categoriaDescricao
    FROM itens i
    INNER JOIN categorias c
    	ON i.categoria_id = c.id
    WHERE quantidade >= :estoque
    ORDER BY quantidade;
    """, countQuery = """
            SELECT COUNT(*)
            FROM itens i
            INNER JOIN categorias c
                ON i.categoria_id = c.id
            WHERE quantidade >= 60
            ORDER BY quantidade;
    """)
    Page<ItemProjection> findByQuantidadePage(Integer estoque, Pageable pageable);

    Integer countByQuantidadeLessThan(Integer quantidade);

}
