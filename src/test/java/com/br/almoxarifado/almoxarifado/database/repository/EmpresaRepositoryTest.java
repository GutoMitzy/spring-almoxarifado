package com.br.almoxarifado.almoxarifado.database.repository;

import com.br.almoxarifado.almoxarifado.database.model.EmpresaModel;
import com.br.almoxarifado.almoxarifado.dto.EmpresaDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class EmpresaRepositoryTest {
    @Autowired
    EntityManager entityManager;
    @Autowired
    IEmpresaRepository empresaRepository;

    @Test
    @DisplayName("Should get Empresa successfulliy from DB")
    void findEmpresaByNomeSuccess() {
        String nome = "Augusto";
        EmpresaDto empresaDto = new EmpresaDto(nome, "a@a.com", "9 999", "Rua X", "LTDA");
        this.createEmpresa(empresaDto);

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

    private EmpresaModel createEmpresa(EmpresaDto empresaDto) {
        EmpresaModel empresa = new EmpresaModel(empresaDto);
        entityManager.persist(empresa);
        return empresa;
    }
}