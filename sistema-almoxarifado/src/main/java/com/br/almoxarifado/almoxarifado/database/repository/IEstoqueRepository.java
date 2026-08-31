package com.br.almoxarifado.almoxarifado.database.repository;

import com.br.almoxarifado.almoxarifado.database.model.EstoqueModel;
import com.br.almoxarifado.almoxarifado.dto.projection.CategoriaContagemProjection;
import com.br.almoxarifado.almoxarifado.dto.projection.EstoqueContagemProjection;
import com.br.almoxarifado.almoxarifado.dto.projection.ItemProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEstoqueRepository extends JpaRepository<EstoqueModel, Integer> {

    @NativeQuery(value = """
    SELECT  e.id                 estoqueId,
            e.quantidade         quantidade,
            i.nome               itemNome,
            i.descricao          descricao,
            c.nome				 categoriaNome,
            c.descricao			 categoriaDescricao
    FROM estoques e
    INNER JOIN itens i
    	ON e.id = i.id
    INNER JOIN categorias c
    	ON i.categoria_id = c.id
    WHERE quantidade >= :estoque
    ORDER BY quantidade;
    """, countQuery = """
            SELECT COUNT(*)
            FROM estoques e
            INNER JOIN itens i
                ON e.id = i.id
            INNER JOIN categorias c
                ON i.categoria_id = c.id
            WHERE quantidade >= 60
            ORDER BY quantidade;
    """)
    Page<ItemProjection> findItemsByStock(Integer estoque, Pageable pageable);

    @NativeQuery("""
        SELECT e.quantidade, p.nome 
        FROM estoques e 
        INNER JOIN itens p
            ON e.peca_id = p.id
        ORDER BY quantidade
    """)
    List<EstoqueContagemProjection> contarItensPorCategoria();
}
