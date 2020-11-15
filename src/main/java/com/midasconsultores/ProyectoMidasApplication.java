package com.midasconsultores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProyectoMidasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoMidasApplication.class, args);
	}

}
