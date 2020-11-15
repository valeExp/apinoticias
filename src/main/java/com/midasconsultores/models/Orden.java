package com.midasconsultores.models;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Orden {
	ASC,DESC;
	
	public static boolean contiene( String orden ) {
		
		return Arrays.asList( values() )
			     .stream()
				 .map( Orden::name )
				 .filter( name -> name.equals(orden))
				 .findAny()
				 .isPresent();
								 
	}
	
	public static String valoresStr() {
		return Arrays.asList( values())
			         .stream().map( Orden::name ).collect( Collectors.joining(",") );	
	}
}
