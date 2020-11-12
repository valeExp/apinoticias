package com.midasconsultores.services.impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.midasconsultores.entities.Persona;
import com.midasconsultores.repositories.PersonaRepository;
import com.midasconsultores.services.IPersonaService;


@Service
public class PersonaServiceImpl implements IPersonaService {
	
	@Autowired
	PersonaRepository personaRepository;

	@Override
	public Page<Persona> findAll( Pageable pageable ) {
		return personaRepository.findAll(pageable);
	}

	@Override
	public Optional<Persona> findById( Long id ) {
		return personaRepository.findById(id);
	} 

	@Override
    public Persona save( Persona persona ) {
    	return personaRepository.save( persona );
    }

}
