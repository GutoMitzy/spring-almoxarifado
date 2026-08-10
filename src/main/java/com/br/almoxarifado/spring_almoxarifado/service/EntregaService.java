package com.br.almoxarifado.spring_almoxarifado.service;

import com.br.almoxarifado.spring_almoxarifado.database.model.EntregaModel;
import com.br.almoxarifado.spring_almoxarifado.database.model.PecaModel;
import com.br.almoxarifado.spring_almoxarifado.database.repository.IEntregasRepository;
import com.br.almoxarifado.spring_almoxarifado.database.repository.IPecasRepository;
import com.br.almoxarifado.spring_almoxarifado.dto.EntregaDto;
import com.br.almoxarifado.spring_almoxarifado.dto.EntregaProjection;
import com.br.almoxarifado.spring_almoxarifado.dto.PecaDto;
import com.br.almoxarifado.spring_almoxarifado.exception.BadRequestException;
import com.br.almoxarifado.spring_almoxarifado.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EntregaService {
    private final IEntregasRepository entregasRepository;
    private final IPecasRepository pecasRepository;
    private final CorredorService corredorService;

    public void createEntrega(EntregaDto entregaDto) {
        List<PecaModel> pecas = new ArrayList<>();
        List<Integer> quantidades = new ArrayList<>();
        for(PecaDto dto : entregaDto.getPecas()) {
            PecaModel peca = pecasRepository.findByNome(dto.getNome())
                    .orElse(null);

            if(peca == null) {
                peca = pecasRepository.save(PecaModel.builder()
                                .nome(dto.getNome())
                                .descricao(dto.getDescricao())
                                .quantidade(0)
                        .build());
            }

            pecas.add(peca);
            quantidades.add(dto.getQuantidade());
        }

        EntregaModel entrega = EntregaModel.builder()
                .fornecedor(entregaDto.getFornecedor())
                .dataEntrega(entregaDto.getDataEntrega())
                .pecas(pecas)
                .entregue(false)
                .quantidades(quantidades)
                .build();

        entregasRepository.save(entrega);
    }

    @Transactional(rollbackFor = Exception.class)
    public void concluirEntrega(Integer entregaId) {

        EntregaModel entrega = entregasRepository.findById(entregaId)
                .orElseThrow(() -> new NotFoundException("Entrega não encontrada!"));

        if (entrega.getEntregue()) {
            throw new BadRequestException("Entrega já concluída!");
        }

        List<PecaModel> pecas = entrega.getPecas();
        List<Integer> quantidades = entrega.getQuantidades();

        if (pecas.size() != quantidades.size()) {
            throw new BadRequestException(
                    "Inconsistência entre a lista de peças e a lista de quantidades da entrega.");
        }

        for (int i = 0; i < pecas.size(); i++) {
            PecaModel peca = pecas.get(i);
            int quantidadeRecebida = quantidades.get(i);

            if (quantidadeRecebida <= 0) {
                throw new BadRequestException(String.format(
                        "A quantidade recebida da peça %s deve ser maior que zero.", peca.getId()));
            }

            peca.setQuantidade(peca.getQuantidade() + quantidadeRecebida);
            pecasRepository.save(peca);

            corredorService.findCorredorReceptaculoDisponivel(peca, quantidadeRecebida);
        }

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
