package com.br.almoxarifado.almoxarifado.database.repository;

import com.br.almoxarifado.almoxarifado.database.model.ReceptaculoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReceptaculoRepository extends JpaRepository<ReceptaculoModel, Integer> {
}
