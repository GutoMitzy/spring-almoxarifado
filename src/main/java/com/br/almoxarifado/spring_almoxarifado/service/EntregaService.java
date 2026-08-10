package com.br.almoxarifado.spring_almoxarifado.service;

import com.br.almoxarifado.spring_almoxarifado.database.model.EntregaModel;
import com.br.almoxarifado.spring_almoxarifado.database.model.PecaModel;
import com.br.almoxarifado.spring_almoxarifado.database.repository.IEntregasRepository;
import com.br.almoxarifado.spring_almoxarifado.database.repository.IPecasRepository;
import com.br.almoxarifado.spring_almoxarifado.dto.EntregaDto;
import com.br.almoxarifado.spring_almoxarifado.dto.EntregaProjection;
import com.br.almoxarifado.spring_almoxarifado.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EntregaService {
    private final IEntregasRepository entregasRepository;
    private final IPecasRepository pecasRepository;

    public void createEntrega(EntregaDto entregaDto) {
        Set<PecaModel> pecas = new HashSet<>();

        for(Integer pecaId : entregaDto.getPecas()) {
            PecaModel peca = pecasRepository.findById(pecaId)
                    .orElseThrow(() -> new NotFoundException(String.format("Peça %s não encontrada!", pecaId)));

            pecas.add(peca);
        }

        EntregaModel entrega = EntregaModel.builder()
                .fornecedor(entregaDto.getFornecedor())
                .dataEntrega(entregaDto.getDataEntrega())
                .pecas(pecas)
                .entregue(false)
                .build();

        entregasRepository.save(entrega);
    }

    public void concluirEntrega(Integer entregaId) {
        EntregaModel entrega = entregasRepository.findById(entregaId)
                .orElseThrow(() -> new NotFoundException("Entrega não encontrada!"));

        entrega.setEntregue(true);
        entregasRepository.save(entrega);

    }

    public Page<EntregaProjection> findAllEntregasPage(Integer page, Integer size) {
        return entregasRepository.findAllEntregasPage(PageRequest.of(page, size));
    }

    public List<EntregaModel> findAllEntregas() {
        return entregasRepository.findAll();
    }
}
