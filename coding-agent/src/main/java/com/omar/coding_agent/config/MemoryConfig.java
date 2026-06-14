package com.omar.coding_agent.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

    @Bean
    public ChatMemory chatMemory(
            @Value("${agent.memory.max-messages:50}") int maxMessages
    ) {
        return MessageWindowChatMemory.builder()
                .maxMessages(maxMessages)
                .build();
    }
}