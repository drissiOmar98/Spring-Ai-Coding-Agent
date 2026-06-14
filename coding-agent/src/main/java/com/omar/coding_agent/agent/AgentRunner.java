package com.omar.coding_agent.agent;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AgentRunner implements CommandLineRunner {

    private final CodingAgent codingAgent;

    @Override
    public void run(String... args) {
        codingAgent.run();
    }
}