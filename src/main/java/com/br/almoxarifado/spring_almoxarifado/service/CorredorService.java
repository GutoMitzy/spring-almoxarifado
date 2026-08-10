package com.br.almoxarifado.spring_almoxarifado.service;

import com.br.almoxarifado.spring_almoxarifado.database.model.CorredorModel;
import com.br.almoxarifado.spring_almoxarifado.database.model.PecaModel;
import com.br.almoxarifado.spring_almoxarifado.database.model.ReceptaculoModel;
import com.br.almoxarifado.spring_almoxarifado.database.repository.ICorredoresRepository;
import com.br.almoxarifado.spring_almoxarifado.database.repository.IReceptaculosRepository;
import com.br.almoxarifado.spring_almoxarifado.dto.CorredorProjection;
import com.br.almoxarifado.spring_almoxarifado.exception.BadRequestException;
import com.br.almoxarifado.spring_almoxarifado.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CorredorService {
    private final ICorredoresRepository corredoresRepository;
    private final IReceptaculosRepository receptaculosRepository;

    public CorredorModel findById(Integer corredorId) {
        return corredoresRepository.findById(corredorId)
                .orElseThrow(() -> new NotFoundException("Corredor não encontrado!"));
    }

    public Page<CorredorProjection> findAllReceptaculosByCorredorPage(Integer corredorId, Integer page, Integer size) {
        return corredoresRepository.findAllReceptaculosByCorredorPage(corredorId, PageRequest.of(page, size));
    }

    //Buscar corredor e receptaculo disponivel
    public void findCorredorReceptaculoDisponivel(PecaModel peca, int quantidadeRestante) {

        List<CorredorModel> corredores = corredoresRepository.findAll();

        buscaComPeca:
        for (CorredorModel corredor : corredores) {
            for (ReceptaculoModel receptaculo : corredor.getReceptaculos()) {

                if (quantidadeRestante <= 0) {
                    break buscaComPeca;
                }
                if (receptaculo.getPeca() == null || !receptaculo.getPeca().getId().equals(peca.getId())) {
                    continue;
                }

                int capacidadeDisponivel = receptaculo.getCapacidadeDisponivel();

                if (capacidadeDisponivel <= 0) {
                    continue;
                }

                int quantidadeAdicionar = Math.min(quantidadeRestante, capacidadeDisponivel);

                receptaculo.setQuantidadeAtual(receptaculo.getQuantidadeAtual() + quantidadeAdicionar);
                quantidadeRestante -= quantidadeAdicionar;
                receptaculosRepository.save(receptaculo);
            }
        }

        if (quantidadeRestante > 0) {
            buscaVazio:
            for (CorredorModel corredor : corredores) {
                for (ReceptaculoModel receptaculo : corredor.getReceptaculos()) {

                    if (quantidadeRestante <= 0) {
                        break buscaVazio;
                    }
                    if (receptaculo.getPeca() != null || receptaculo.getCapacidade() <= 0) {
                        continue;
                    }

                    int quantidadeAdicionar = Math.min(quantidadeRestante, receptaculo.getCapacidade());

                    receptaculo.setPeca(peca);
                    receptaculo.setQuantidadeAtual(quantidadeAdicionar);
                    quantidadeRestante -= quantidadeAdicionar;
                    receptaculosRepository.save(receptaculo);
                }
            }
        }

        if (quantidadeRestante > 0) {
            throw new BadRequestException(
                    "Não há espaço suficiente para armazenar a peça: " + peca.getNome() + ". Quantidade restante: " + quantidadeRestante);
        }
    }
}
