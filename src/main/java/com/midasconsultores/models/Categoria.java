package com.midasconsultores.models;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum Categoria {
	
	ULTIMAS_NOTICIAS, LOCALES,	NACIONALES,	INTERNACIONALES,
	ECONOMIA, POLITICA,	POLICIALES,	SOCIEDAD, SALUD;

	public static List<Categoria> todasMenosExcluidas( Categoria ...excluidas ) {
		
		Set<Categoria> excluidasList =  Arrays.asList( excluidas ).stream().collect( Collectors.toSet() );
		
		return Arrays.asList( values() )
			  .stream()
			  .filter( categoria -> !excluidasList.contains(categoria) )
			  .collect( Collectors.toList() );
	}
	
	public static String todasMenosExcluidasStr( Categoria ...excluidas ) {
		 return todasMenosExcluidas( Categoria.INTERNACIONALES )
		 .stream()
		 .map( c -> c.name() )
		 .collect( Collectors.joining(",") );
	}
	
	
	
}
