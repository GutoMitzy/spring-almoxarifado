package com.br.almoxarifado.spring_almoxarifado.database.repository;

import com.br.almoxarifado.spring_almoxarifado.database.model.CorredorModel;
import com.br.almoxarifado.spring_almoxarifado.dto.CorredorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ICorredoresRepository extends JpaRepository<CorredorModel, Integer> {
    @NativeQuery(value = """
    SELECT
        cr.corredor_id       AS corredorId,
        cr.receptaculo_id    AS receptaculoId,
        r.quantidade_atual      AS quantidadeAtual,
        r.peca_id           AS pecaId,
        p.nome               AS nome,
        p.descricao          AS descricao,
        r.quantidade_atual            AS quantidade
    FROM corredores_receptaculos cr
    INNER JOIN receptaculos r
        ON cr.receptaculo_id = r.receptaculo_id
    INNER JOIN pecas p
        ON p.peca_id = r.peca_id
    WHERE cr.corredor_id = :corredorId
    """,
            countQuery = """
        SELECT COUNT(*)
        FROM receptaculos_corredores cr
        INNER JOIN receptaculos r
            ON cr.peca_id = r.peca_id
        INNER JOIN pecas p
            ON p.peca_id = r.peca_id
        """
    )
    Page<CorredorProjection> findAllReceptaculosByCorredorPage(@Param("corredorId") Integer corredorId, Pageable pageable);
}
