package com.br.almoxarifado.almoxarifado.service;

import com.br.almoxarifado.almoxarifado.database.model.CategoriaModel;
import com.br.almoxarifado.almoxarifado.database.model.CorredorModel;
import com.br.almoxarifado.almoxarifado.database.model.ItemModel;
import com.br.almoxarifado.almoxarifado.database.model.ReceptaculoModel;
import com.br.almoxarifado.almoxarifado.database.repository.ICorredorRepository;
import com.br.almoxarifado.almoxarifado.database.repository.IReceptaculoRepository;
import com.br.almoxarifado.almoxarifado.dto.CorredorDto;
import com.br.almoxarifado.almoxarifado.dto.CorredorProjection;
import com.br.almoxarifado.almoxarifado.exception.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CorredorService {
    private final ICorredorRepository corredorRepository;
    private final IReceptaculoRepository receptaculosRepository;

    private final CategoriaService categoriaService;

    @Transactional(rollbackOn =  Exception.class)
    public void addItemReceptaculo(CategoriaModel categoria, ItemModel item, Integer quantidadeRecebida) {
        List<CorredorModel> corredores = corredorRepository.findByCategoria(categoria);
        if(corredores.isEmpty()) { throw new BadRequestException("Não foi possível encontrar corredor da categoria!"); }

        //verificar qual corredor da categoria tem receptaculos disponiveis
        buscaReceptaculo:
        for(CorredorModel corredor : corredores) {
            for(ReceptaculoModel receptaculo : corredor.getReceptaculos()) {
                if(quantidadeRecebida <= 0) {
                    break buscaReceptaculo;
                }

                if(receptaculo.getItem()==null || !receptaculo.getItem().getId().equals(item.getId())) {
                    continue ;
                }

                Integer capacidadeDisponivel = receptaculo.getCapacidadeDisponivel();
                if(capacidadeDisponivel <= 0) {
                    continue;
                }

                Integer quantidadeAdicionar = Math.min(capacidadeDisponivel, quantidadeRecebida);
                receptaculo.addQuantidade(quantidadeAdicionar);
                quantidadeRecebida -= quantidadeAdicionar;
                receptaculosRepository.save(receptaculo);
            }
        }

        if(quantidadeRecebida > 0) {
            buscaVazio:
            for(CorredorModel corredor : corredores) {
                for(ReceptaculoModel receptaculo : corredor.getReceptaculos()) {
                    if(quantidadeRecebida <= 0) {
                        break buscaVazio;
                    }

                    if(receptaculo.getItem()!=null || receptaculo.getCapacidadeDisponivel() <= 0) {
                        continue;
                    }

                    int quantidadeAdicionar = Math.min(quantidadeRecebida, receptaculo.getCapacidade());

                    receptaculo.setItem(item);
                    receptaculo.addQuantidade(quantidadeAdicionar);
                    quantidadeRecebida -= quantidadeAdicionar;
                    receptaculosRepository.save(receptaculo);
                }
            }
        }

        if (quantidadeRecebida > 0) {
            throw new BadRequestException(
                    "Não há espaço suficiente para armazenar a peça: " + item.getNome() + ". Quantidade restante: " + quantidadeRecebida);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeItemReceptaculo(CategoriaModel categoria, ItemModel item, Integer quantidadeEnviada) {
        List<CorredorModel> corredores = corredorRepository.findByCategoria(categoria);

        if (corredores.isEmpty()) {
            throw new BadRequestException("Não foi possível encontrar corredor da categoria!");
        }

        buscaReceptaculo:
        for (CorredorModel corredor : corredores) {
            for (ReceptaculoModel receptaculo : corredor.getReceptaculos()) {
                if (quantidadeEnviada <= 0) {
                    break buscaReceptaculo;
                }
                if (receptaculo.getItem() == null || !receptaculo.getItem().getId().equals(item.getId())) {
                    continue;
                }

                Integer quantidadeDisponivel = receptaculo.getEmUso();
                if (quantidadeDisponivel <= 0) {
                    continue;
                }

                Integer quantidadeRemover = Math.min(quantidadeDisponivel, quantidadeEnviada);

                receptaculo.subtractQuantidade(quantidadeRemover);
                quantidadeEnviada -= quantidadeRemover;

                receptaculosRepository.save(receptaculo);
            }
        }

        if (quantidadeEnviada > 0) {
            throw new BadRequestException("Não há quantidade suficiente do item " + item.getNome() +
                    " nos receptáculos. Quantidade restante: " + quantidadeEnviada
            );
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void createCorredor(CorredorDto corredorDto) {
        Integer quantidadeReceptaculos = corredorDto.getReceptaculos();
        List<ReceptaculoModel> receptaculos = new ArrayList<>();
        while(quantidadeReceptaculos > 0) {
            ReceptaculoModel receptaculo = new ReceptaculoModel();
            receptaculos.add(receptaculo);
            receptaculosRepository.save(receptaculo);
            quantidadeReceptaculos--;
        }

        CategoriaModel categoria = categoriaService.findByNome(corredorDto.getCategoria());

        corredorRepository.save(CorredorModel.builder()
                        .categoria(categoria)
                        .receptaculos(receptaculos)
                .build());
    }

    public Page<CorredorProjection> findCorredorByIdPage(Integer id, Integer page, Integer size) {
        return corredorRepository.findCorredorByIdPage(id, PageRequest.of(page, size));
    }


}
