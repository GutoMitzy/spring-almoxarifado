package com.br.almoxarifado.almoxarifado.assistant.tools;

import com.br.almoxarifado.almoxarifado.assistant.factory.AiAssistantFactory;
import com.br.almoxarifado.almoxarifado.database.repository.*;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstoqueTools {
    private final AiAssistantFactory aiModel;

    private final IEstoqueRepository estoqueRepository;
    private final IItemRepository itemRepository;
    private final IItemTransporteRepository itemTransporteRepository;

    @Tool("Retorna a quantidade total de itens distintos cadastrados na tabela 'itens'")
    public long contarItensCadastrados() {
        return itemRepository.count();
    }

}
