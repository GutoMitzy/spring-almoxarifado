package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.database.model.EmpresaModel;
import com.br.almoxarifado.almoxarifado.database.repository.IEmpresaRepository;
import com.br.almoxarifado.almoxarifado.dto.EmpresaDto;
import com.br.almoxarifado.almoxarifado.exception.BadRequestException;
import com.br.almoxarifado.almoxarifado.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpresaService {
    private final IEmpresaRepository empresaRepository;

    public void createEmpresa(EmpresaDto empresaDto) {
        EmpresaModel empresaModel = empresaRepository.findByNome(empresaDto.getNome())
                .orElse(null);

        if(empresaModel != null) {
            throw new BadRequestException("Empresa ja cadastrada!");
        }

        empresaRepository.save(new EmpresaModel(empresaDto));
    }

    public EmpresaModel findByNome(String nome) {
        return empresaRepository.findByNome(nome)
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada!"));
    }
}
