package com.br.almoxarifado.almoxarifado.database.repository;

import com.br.almoxarifado.almoxarifado.database.model.ItemModel;
import com.br.almoxarifado.almoxarifado.dto.projection.CategoriaContagemProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IItemRepository extends JpaRepository<ItemModel, Integer> {
    Page<ItemModel> findByCategoriaNome(String categoria, Pageable pageable);

    Optional<ItemModel> findByNome(String nome);

    List<CategoriaContagemProjection> countByCategoriaNome(String categoria);

    Integer countByQuantidadeLessThan(@Param("estoque") Integer estoque);

    Page<ItemModel> findByQuantidadeLessThanEqualAndQuantidadeGreaterThanEqual(Integer quantidade, Integer minimo, Pageable pageable);
}
