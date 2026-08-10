package com.br.almoxarifado.spring_almoxarifado.database.repository;

import com.br.almoxarifado.spring_almoxarifado.database.model.EntregaModel;
import com.br.almoxarifado.spring_almoxarifado.dto.EntregaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface IEntregasRepository extends JpaRepository<EntregaModel, Integer> {
    @NativeQuery(value = """
    SELECT  e.id            AS entregaId,
            e.fornecedor    AS fornecedor,
            e.data_entrega  AS dataEntrega,
            e.entregue      AS entregue,
            p.peca_id       AS pecas,
            p.descricao     AS descricao,
            p.nome          AS nome,
            p.estoque       AS estoque
    FROM entregas e
    INNER JOIN pecas p
    """, countQuery = """
    SELECT COUNT(e.id)
    FROM entregas e
    INNER JOIN pecas p
    """)
    Page<EntregaProjection> findAllEntregasPage(Pageable pageable);
}
