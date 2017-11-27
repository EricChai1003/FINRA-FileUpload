package com.example.demo;

import com.example.demo.service.FileUploadService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FinraDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinraDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(FileUploadService uploadService) {
		return (args) -> {
			uploadService.deleteAll();
			uploadService.init();
		};
	}
}
