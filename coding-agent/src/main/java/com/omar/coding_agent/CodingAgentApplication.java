package com.omar.coding_agent;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CodingAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingAgentApplication.class, args);
	}

	@Value("${agent.working-dir}")
	private String workingDir;

	@PostConstruct
	public void init() {
		System.out.println("Working Directory = " + workingDir);
	}


}
