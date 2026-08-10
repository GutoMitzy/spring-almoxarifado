package com.br.almoxarifado.spring_almoxarifado.service;

import com.br.almoxarifado.spring_almoxarifado.database.model.PecaModel;
import com.br.almoxarifado.spring_almoxarifado.database.repository.IPecasRepository;
import com.br.almoxarifado.spring_almoxarifado.dto.PecaDto;
import com.br.almoxarifado.spring_almoxarifado.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PecaService {
    private final IPecasRepository pecaRepository;

    public List<PecaModel> findAll() {
        return pecaRepository.findAll();
    }

    public void createPeca(PecaDto pecaDto) {
        PecaModel peca = pecaRepository.findByNome(pecaDto.getNome())
                .orElse(null);

        if(peca != null) {
            throw new BadRequestException("Peca já existente no banco de dados!");
        }

        pecaRepository.save(PecaModel.builder()
                        .nome(pecaDto.getNome())
                        .descricao(pecaDto.getDescricao())
                        .estoque(pecaDto.getEstoque())
                .build());
    }
}
