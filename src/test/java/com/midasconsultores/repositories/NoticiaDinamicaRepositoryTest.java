package com.midasconsultores.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.entities.Noticia;
import com.midasconsultores.utilities.Utilities;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoticiaDinamicaRepositoryTest {
	
	
	@Autowired
	NoticiaDinamicaRepository noticiaDinamicaRepository;

	@Test
	void test() {
		
		Date fecha = Utilities.stringToDate("05/11/2020", Utilities.FORMAT_DATE);
		
		Paginacion<Noticia> paginacion = noticiaDinamicaRepository.noticiasConFiltro( null, null, null, -1, true );
		
		System.out.println( paginacion );
		
		
	}

}
