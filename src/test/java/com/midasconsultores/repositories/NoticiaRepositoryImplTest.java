package com.midasconsultores.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.models.Noticia;
import com.midasconsultores.models.ParamsBusquedaNoticia;
import com.midasconsultores.utilities.Utilities;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoticiaRepositoryImplTest {
	
	
	public static final int pageSize = 50;
	
	@Autowired
	NoticiaRepositoryImpl noticiaDinamicaRepository;

	@Test
	void test() {
		
		Date fecha = Utilities.stringToDate("05/11/2020", Utilities.FORMAT_DATE);
		
		//Paginacion<Noticia> paginacion = noticiaDinamicaRepository.noticiasConFiltro( null, null, "Corona", 1, true );
		
		//System.out.println( paginacion );
		
		
		Map<String, Object> condiciones = new HashMap<>();
		condiciones.put( ParamsBusquedaNoticia.fuente.name(),"LaNacion" );
		//condiciones.put( ParamsBusquedaNoticia.fecha.name(),fecha );
		//condiciones.put( ParamsBusquedaNoticia.titulo.name() ,"Coronavirus" );
		condiciones.put( ParamsBusquedaNoticia.pagina.name() , 1);
		
		assertEquals( 6 ,  calcularTotalPages( 299 ));
		assertEquals( 7 ,  calcularTotalPages( 344 ));
		assertEquals( 7 ,  calcularTotalPages( 350 ));
		assertEquals( 8 ,  calcularTotalPages( 351 ));	
		
		
	}
	
	
	private int calcularTotalPages( long total ) {		
		int pages = 0;		
		if( total > 0 ) {
			long resto = total % pageSize;
			
			pages = (resto > 0) ? (int)(total / pageSize) + 1:(int)(total / pageSize);
		}
		return 	pages;	
	}

}
