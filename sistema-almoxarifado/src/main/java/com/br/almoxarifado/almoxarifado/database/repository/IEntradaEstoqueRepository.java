package com.br.almoxarifado.almoxarifado.database.repository;

import com.br.almoxarifado.almoxarifado.database.model.EntradaEstoqueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface IEntradaEstoqueRepository extends JpaRepository<EntradaEstoqueModel, Integer> {
    Integer countByDataRegistroEquals(LocalDate data);
}
