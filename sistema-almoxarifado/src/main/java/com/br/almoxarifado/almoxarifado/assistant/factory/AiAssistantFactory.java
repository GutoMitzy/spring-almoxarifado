package com.br.almoxarifado.almoxarifado.assistant.factory;

import com.br.almoxarifado.almoxarifado.assistant.tools.EstoqueTools;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Configuration
public class AiAssistantFactory {

    @Bean
    public static ChatModel createOllama() {
        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("qwen2.5:3b")
                .timeout(Duration.ofSeconds(300))
                .build();
    }

    @Bean
    public AiAssistantFormat almoxarifadoAssistant(ChatModel chatModel, EstoqueTools estoqueTools) {
        return AiServices.builder(AiAssistantFormat.class)
                .chatModel(chatModel)
                .tools(estoqueTools)
                .build();
    }
}
