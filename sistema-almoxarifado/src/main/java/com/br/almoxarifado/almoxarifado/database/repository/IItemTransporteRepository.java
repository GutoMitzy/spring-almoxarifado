package com.br.almoxarifado.almoxarifado.database.repository;

import com.br.almoxarifado.almoxarifado.database.model.ItemTransporteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItemTransporteRepository extends JpaRepository<ItemTransporteModel, Integer> {
}
