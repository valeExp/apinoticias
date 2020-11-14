package com.midasconsultores.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CategoriaTest {

	@Test
	void testExcluyeCategorias() {
		
		String deseado = "ULTIMAS_NOTICIAS,LOCALES,NACIONALES,ECONOMIA,POLITICA,POLICIALES,SOCIEDAD,SALUD";
		
		assertEquals( deseado,  Categoria.todasMenosExcluidasStr( Categoria.INTERNACIONALES ) );
									 
	}

}
