package com.br.almoxarifado.spring_almoxarifado.database.repository;

import com.br.almoxarifado.spring_almoxarifado.database.model.PecaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPecasRepository extends JpaRepository<PecaModel, Integer> {
    Optional<PecaModel> findByNome(String nome);
}
