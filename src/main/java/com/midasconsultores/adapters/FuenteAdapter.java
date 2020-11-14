package com.midasconsultores.adapters;

import com.midasconsultores.cliente.Provider;
import com.midasconsultores.models.Fuente;

public class FuenteAdapter {
	
	public static Fuente providerToFuente( Provider provider ) {
		
		Fuente fuente = new Fuente();
		fuente.setId( provider.get_id() );
		fuente.setNombre( provider.getName() );
		fuente.setAlcance( provider.getScope() );
		
		return fuente;
	}

}
