package com.midasconsultores.jobs;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.midasconsultores.models.Estado;
import com.midasconsultores.models.LogNoticias;
import com.midasconsultores.models.Noticia;
import com.midasconsultores.services.ILogNoticiasService;
import com.midasconsultores.services.INoticiaService;
import com.midasconsultores.utilities.Utilities;

@Component
public class DepuracionBaseJob {
	
	@Autowired
	ILogNoticiasService logNoticiasService;
	@Autowired
	INoticiaService noticiaService;

	@Scheduled(cron = "0 * * * * *", zone="America/Argentina/Buenos_Aires" )
	//@Scheduled(cron = "0 0 2 * * *", zone="America/Argentina/Buenos_Aires" )
	void depurarNoticias() {
		
		Date fechaDepuracion = Utilities.fechaAddDia( new Date(), -5 );
							
		Long cantidadRegistros = 0L;
		List<Noticia> noticias = noticiaService.getNoticiasByFechaPublicacion( fechaDepuracion );			
		
		for( Noticia noti: noticias) {
		   try {
			   noticiaService.eliminarNoticia(noti);
			  cantidadRegistros++;
		  }catch( Exception ex ){
			  
		  }
		}		
		
		LogNoticias log = new LogNoticias();		
		log.setEstado( ( cantidadRegistros == noticias.size() )? Estado.EXITO:Estado.ERROR );
		log.setMensaje( "Ejecutado"
						.concat( ( cantidadRegistros == noticias.size() )? " sin errores ":" con errores " )
						.concat( cantidadRegistros.toString()).concat(" registros eliminados") );
		logNoticiasService.save(log);		
	
		
	}
}

