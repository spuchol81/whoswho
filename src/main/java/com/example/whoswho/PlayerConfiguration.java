package com.example.whoswho;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerConfiguration {
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.defaultAdvisors().build();
    }
    @Bean
    public SimpleVectorStore movieVectorStoreInitBean(EmbeddingModel embeddingModel) {
        return new SimpleVectorStore(embeddingModel);
    } 
}
