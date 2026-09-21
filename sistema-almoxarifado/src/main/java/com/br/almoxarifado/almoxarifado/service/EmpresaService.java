package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.database.model.EmpresaModel;
import com.br.almoxarifado.almoxarifado.database.repository.IEmpresaRepository;
import com.br.almoxarifado.almoxarifado.dto.EmpresaSendDto;
import com.br.almoxarifado.almoxarifado.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpresaService {
    private final IEmpresaRepository empresaRepository;

    public EmpresaModel findByNome(String nome) {
        return empresaRepository.findByNome(nome)
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada!"));
    }

    public Page<EmpresaSendDto> findByNomePage(String nome, Integer page, Integer size) {
        return empresaRepository.findByNomeContainingIgnoreCase(nome, PageRequest.of(page, size)).map(EmpresaSendDto::toDto);
    }

    public Page<EmpresaSendDto> findAllPage(Integer page, Integer size) {
        return empresaRepository.findAll(PageRequest.of(page, size)).map(EmpresaSendDto::toDto);
    }

}
