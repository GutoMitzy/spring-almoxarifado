package com.br.almoxarifado.spring_almoxarifado.service;

import com.br.almoxarifado.spring_almoxarifado.database.model.EmpresaModel;
import com.br.almoxarifado.spring_almoxarifado.database.repository.IEmpresaRepository;
import com.br.almoxarifado.spring_almoxarifado.dto.EmpresaDto;
import com.br.almoxarifado.spring_almoxarifado.exception.BadRequestException;
import com.br.almoxarifado.spring_almoxarifado.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {
    private final IEmpresaRepository empresaRepository;

    public EmpresaModel getEmpresaById(Integer empresaId) {
        return empresaRepository.findById(empresaId)
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada!"));
    }

    public List<EmpresaModel> getAllEmpresas() {
        return empresaRepository.findAll();
    }

    public void saveEmpresa(EmpresaDto empresaDto) {
        EmpresaModel empresa = empresaRepository.findByNome(empresaDto.getNome())
                .orElse(null);

        if (empresa != null) {
            throw new BadRequestException("Não foi possível criar a empresa!");
        }

        empresaRepository.save(EmpresaModel.builder()
                        .nome(empresaDto.getNome())
                        .endereco(empresaDto.getEndereco())
                        .telefone(empresaDto.getTelefone())
                .build());
    }
}
