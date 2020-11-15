package com.midasconsultores.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.midasconsultores.models.Estado;
import com.midasconsultores.models.LogNoticias;
import com.midasconsultores.services.ILogNoticiasService;

@Component
public class DepuracionBaseJob {
	
	@Autowired
	ILogNoticiasService logNoticiasService;

	@Scheduled(cron = "0 * 17 * * *", zone="America/Argentina/Buenos_Aires" )
	//@Scheduled(cron = "0 0 2 * * *", zone="America/Argentina/Buenos_Aires" )
	void depurarNoticias() {
		
		
		System.out.println( "Hola que tal");
		
		LogNoticias log = new LogNoticias();		
		log.setEstado(Estado.ERROR);
		log.setMensaje("parece que hubo un error");
		
		logNoticiasService.save(log);
		
	}
}
