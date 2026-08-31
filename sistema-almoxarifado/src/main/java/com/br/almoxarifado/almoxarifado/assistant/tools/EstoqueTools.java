package com.br.almoxarifado.almoxarifado.assistant.tools;

import com.br.almoxarifado.almoxarifado.assistant.factory.AiAssistantFactory;
import com.br.almoxarifado.almoxarifado.database.repository.*;
import com.br.almoxarifado.almoxarifado.dto.projection.CategoriaContagemProjection;
import com.br.almoxarifado.almoxarifado.dto.projection.EstoqueContagemProjection;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EstoqueTools {
    private final AiAssistantFactory aiModel;

    private final IEstoqueRepository estoqueRepository;
    private final IItemRepository itemRepository;
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

}
