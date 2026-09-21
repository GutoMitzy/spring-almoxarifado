package com.br.almoxarifado.almoxarifado.database.repository;

import com.br.almoxarifado.almoxarifado.database.model.EmpresaModel;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmpresaRepositoryTest {
    @Autowired
    EntityManager entityManager;
    @Autowired
    IEmpresaRepository empresaRepository;

    @Test
    @DisplayName("Should get Empresa successfulliy from DB")
    void findEmpresaByNomeSuccess() {
        String nome = "Augusto";
        EmpresaCreateDto empresaCreateDto = new EmpresaCreateDto(nome, "a@a.com", "9 999", "Rua X", "LTDA", "Eletrônica");
        this.createEmpresa(empresaCreateDto);

        Optional<EmpresaModel> empresa = this.empresaRepository.findByNome(nome);
        assertThat(empresa.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Empresa from DB when empresa not exists")
    void findEmpresaByNomeError() {
        String nome = "Augusto";

        Optional<EmpresaModel> empresa = this.empresaRepository.findByNome(nome);
        assertThat(empresa.isEmpty()).isTrue();
    }

    private EmpresaModel createEmpresa(EmpresaCreateDto empresaCreateDto) {
        EmpresaModel empresa = new EmpresaModel(empresaCreateDto);
        entityManager.persist(empresa);
        return empresa;
    }
}