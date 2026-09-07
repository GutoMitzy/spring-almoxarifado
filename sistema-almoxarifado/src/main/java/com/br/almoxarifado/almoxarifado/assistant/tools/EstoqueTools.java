package com.br.almoxarifado.almoxarifado.assistant.tools;

import com.br.almoxarifado.almoxarifado.assistant.factory.AiAssistantFactory;
import com.br.almoxarifado.almoxarifado.database.model.ReceptaculoModel;
import com.br.almoxarifado.almoxarifado.database.repository.*;
import com.br.almoxarifado.almoxarifado.dto.projection.CategoriaContagemProjection;
import com.br.almoxarifado.almoxarifado.dto.projection.CorredorProjection;
import com.br.almoxarifado.almoxarifado.dto.projection.EstoqueContagemProjection;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EstoqueTools {
    private final AiAssistantFactory aiModel;

    private final IEstoqueRepository estoqueRepository;
    private final IItemRepository itemRepository;
    private final ICorredorRepository corredorRepository;
    private final IItemTransporteRepository itemTransporteRepository;

    private <T> String formatarLista(List<T> lista, Function<T, String> formatador, String mensagemVazia) {
        if (lista.isEmpty()) {
            return mensagemVazia;
        }
        return lista.stream()
                .map(formatador)
                .collect(Collectors.joining("\n"));
    }

    @Tool("Retorna a quantidade total de itens distintos cadastrados na tabela 'itens'")
    public long contarItensCadastrados() {
        return itemRepository.count();
    }

    @Tool("Retorna lista de quantos itens existem para cada categoria registrada")
    public String contarItensPorCategoria() {
        List<CategoriaContagemProjection> resultado = itemRepository.contarItensPorCategoria();

        return formatarLista(resultado,
                r -> r.getCategoria() + " : " + r.getQuantidade(),
                "Nenhuma categoria registrada.");
    }

    @Tool("Retorna a quantidade em estoque para cada item registrado")
    public String contarEstoquePorItem() {
        List<EstoqueContagemProjection> resultado = estoqueRepository.contarItensPorCategoria();

        return formatarLista(resultado,
                r -> r.getNome() + " : " + r.getQuantidade(),
                "Nenhum item em estoque registrado.");
    }

    @Tool("""
            Retorna informações detalhadas sobre o estoque dos corredores.
            Para cada corredor, siga a estrutura:
            - Corredor: (número do corredor)\n
                - Categoria: (categoria)\n
                - Capacidade total: (capacidade total)\n
                - Capacidade em uso: (capacidade em uso)\n
                - Capacidade disponível: (capacidade disponível)\n\n
    """)
    public String infoEstoqueCorredor() {
        List<CorredorProjection> resultado = corredorRepository.findCorredoresInfo();

        Integer capacidadeReceptaculo = ReceptaculoModel.capacidade;

        return resultado.stream()
                .collect(Collectors.groupingBy(
                        CorredorProjection::getCorredorId,
                        LinkedHashMap::new,
                        Collectors.toList()
                ))
                .values()
                .stream()
                .map(lista -> {

                    CorredorProjection corredor = lista.get(0);

                    int capacidadeTotal = lista.size() * ReceptaculoModel.capacidade;

                    int capacidadeEmUso = lista.stream()
                            .mapToInt(r -> r.getQuantidadeAtual() != null ? r.getQuantidadeAtual() : 0)
                            .sum();

                    int capacidadeDisponivel = capacidadeTotal - capacidadeEmUso;

                    return "Corredor " + corredor.getCorredorId()
                            + ": " + corredor.getCategoriaNome()
                            + " (" + corredor.getCategoriaDescricao() + ")"
                            + " | Capacidade total: " + capacidadeTotal
                            + " | Capacidade disponível: " + capacidadeDisponivel
                            + " | Capacidade em uso: " + capacidadeEmUso;
                })
                .collect(Collectors.joining("\n"));
    }
}
