package com.midasconsultores.services;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.midasconsultores.entities.Persona;

public interface IPersonaService {
	
	public  Page<Persona>  findAll( Pageable pageable );
	public  Optional<Persona> findById( Long id );
	public Persona save( Persona persona ); 

}
