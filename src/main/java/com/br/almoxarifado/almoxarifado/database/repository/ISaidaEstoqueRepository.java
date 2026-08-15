package com.br.almoxarifado.almoxarifado.database.repository;

import com.br.almoxarifado.almoxarifado.database.model.EntradaEstoqueModel;
import com.br.almoxarifado.almoxarifado.database.model.SaidaEstoqueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISaidaEstoqueRepository extends JpaRepository<SaidaEstoqueModel, Integer> {
}
