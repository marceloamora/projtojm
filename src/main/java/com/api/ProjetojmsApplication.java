package com.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.api.service.SMSConsumerFuncionarioService;



@SpringBootApplication
public class ProjetojmsApplication {
	@Autowired
	SMSConsumerFuncionarioService sMSConsumerFuncionarioService;
	public static void main(String[] args) {
		SpringApplication.run(ProjetojmsApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {

				this.sMSConsumerFuncionarioService.run();
			
	
		};
	}

}
