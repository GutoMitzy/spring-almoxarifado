package com.br.almoxarifado.almoxarifado.database.repository;

import com.br.almoxarifado.almoxarifado.database.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolesRepository extends JpaRepository<RoleModel, Integer> {
    Optional<RoleModel> findByNome(String nome);
}

