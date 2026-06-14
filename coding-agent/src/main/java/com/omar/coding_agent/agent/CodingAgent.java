package com.omar.coding_agent.agent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
@Slf4j
public class CodingAgent {

    private final ChatClient chatClient;

    @Value("${agent.working-dir:#{systemProperties['user.dir']}}")
    private String workingDir;

    private static final String CONVERSATION_ID = "default-session";

    public void run() {
        var scanner = new Scanner(System.in);
        System.out.println("🤖 Coding Agent Ready. Ask me anything about your codebase!");

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input.trim())) {
                System.out.println("Goodbye!");
                break;
            }

            try {
                String response = chatClient.prompt(input)
                        .toolContext(Map.of("workingDir", workingDir))
                        .advisors(a -> a.param("chat_memory_conversation_id", CONVERSATION_ID))
                        .call()
                        .content();
                System.out.println("\n" + response);
            } catch (Exception e) {
                log.error("Agent error", e);
                System.out.println("⚠️  Error: " + e.getMessage());
            }
        }
    }
}