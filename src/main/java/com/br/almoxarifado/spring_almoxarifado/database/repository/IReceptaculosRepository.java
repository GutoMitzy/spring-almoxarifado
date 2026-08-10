package com.br.almoxarifado.spring_almoxarifado.database.repository;

import com.br.almoxarifado.spring_almoxarifado.database.model.ReceptaculoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReceptaculosRepository extends JpaRepository<ReceptaculoModel, Integer> {
}
