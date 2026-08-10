package com.br.almoxarifado.spring_almoxarifado.database.repository;

import com.br.almoxarifado.spring_almoxarifado.database.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolesRepository extends JpaRepository<RoleModel, Integer> {
    Optional<RoleModel> findByNome(String nome);
}
