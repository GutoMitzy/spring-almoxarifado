package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.database.model.EmpresaModel;
import com.br.almoxarifado.almoxarifado.database.repository.IEmpresaRepository;
import com.br.almoxarifado.almoxarifado.dto.EmpresaDto;
import com.br.almoxarifado.almoxarifado.exception.BadRequestException;
import com.br.almoxarifado.almoxarifado.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class EmpresaService {
    private final IEmpresaRepository empresaRepository;

    public EmpresaModel findByNome(String nome) {
        return empresaRepository.findByNome(nome)
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada!"));
    }

    public Page<EmpresaDto> findByNomePage(String nome, Integer page, Integer size) {
        return empresaRepository.findByNomeContainingIgnoreCase(nome, PageRequest.of(page, size)).map(EmpresaDto::toDto);
    }

    public Page<EmpresaDto> findAllPage(Integer page, Integer size) {
        return empresaRepository.findAll(PageRequest.of(page, size)).map(EmpresaDto::toDto);
    }

}
