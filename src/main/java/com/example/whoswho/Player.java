package com.example.whoswho;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class Player {
    private static final Logger log = LoggerFactory.getLogger(Player.class);
    private final ChatClient chatClient;
    private final SimpleVectorStore vectorStore;
    @Value("classpath:/static/Cards.json")
    private Resource Cards;

    public Player(ChatClient chatClient, SimpleVectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void addCardsForRag() {
    //This represents all the characters of the player
    log.info("Add  document {} for rag", Cards.getFilename());
    DocumentReader reader = new JsonReader(Cards);
    List<Document> documents = reader.get();
    vectorStore.accept(documents);
    }
    
    public String answerOpponent(String question){
    var advisorSearchRequest = SearchRequest.query(question).withTopK(10);
    return chatClient.prompt()
                        .system("""
                        You are defending during a \"Who's Who.\" game.
                        You have your list of characters in the context, and one of them is selected.
                        You can answer opponent's questions only by yes for him to guess which character is currently selected.""")
                        .user(question)
                        .advisors(new QuestionAnswerAdvisor(vectorStore, advisorSearchRequest))
                        .call().content();
    }
}
