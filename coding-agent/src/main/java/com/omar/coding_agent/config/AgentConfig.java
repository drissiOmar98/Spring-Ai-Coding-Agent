package com.omar.coding_agent.config;

import org.springaicommunity.agent.tools.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.ToolCallingAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig {

    @Bean
    public ChatClient chatClient(
            ChatClient.Builder builder,
            ChatMemory chatMemory,
            @Value("${agent.working-dir:#{systemProperties['user.dir']}}") String workingDir
    ) {
        return builder
                .defaultSystem("""
                        You are a helpful coding assistant. You have access to tools
                        for reading files, searching code, running shell commands,
                        and editing files. Use them to help the user with their codebase.
                        
                        Current directory: %s
                        Operating system: Windows (use cmd.exe commands: dir, type, mkdir, copy, etc.)
                        Shell: cmd.exe — do NOT use bash/unix commands (ls, cat, grep, touch, etc.)
                        """.formatted(workingDir))
                .defaultTools(
                        FileSystemTools.builder().build(),
                        GrepTool.builder().build(),
                        GlobTool.builder().build(),
                        ShellTools.builder().build()
                )
                .defaultTools(
                        SkillsTool.builder()
                                .addSkillsDirectory("D:/Spring AI/Spring AI Autonomous Coding Agent/coding-agent/.claude/skills")
                                .build()
                )
                .defaultAdvisors(
                        ToolCallingAdvisor.builder()
                                .conversationHistoryEnabled(false)
                                .build(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .build();
    }
}