package com.example.Mid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.beans.BeanProperty;

@SpringBootApplication
public class MidApplication {
	@Bean
	public WebClient webClient() {
		return WebClient.create("http://localhost:5000/db"); }
	public static void main(String[] args) {
		SpringApplication.run(MidApplication.class, args);
	}

}
