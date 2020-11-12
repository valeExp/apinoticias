package com.midasconsultores.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.midasconsultores.entities.Persona;
import com.midasconsultores.services.IPersonaService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/personas")
public class PersonaController {
	
	@Autowired
	IPersonaService personaService;
	
	@ApiOperation(value = "Lista todas las personas", nickname = "obtenerTodas")
	@GetMapping
	@ResponseStatus( HttpStatus.OK  )
	public ResponseEntity<Page<Persona>> findAll( Pageable pageable ) {
		return ResponseEntity.ok( personaService.findAll(pageable)  );
	}
	
	
	@ApiOperation(value = "Busca por id", nickname = "findById")
	@GetMapping("/{id}")
	public ResponseEntity<Persona> findById( @PathVariable Long id ) {		
		return personaService
			.findById(id)
			.map( ResponseEntity::ok )
			.orElse( ResponseEntity.notFound().build());		
	}
	
	@ApiOperation(value = "Crear una Persona", nickname = "savePersona")
	@PostMapping
	public ResponseEntity<?> savePersona( @Valid @RequestBody Persona persona ) {	
		
		Persona personaSaved = personaService.save(persona);
		return new ResponseEntity<Persona>(personaSaved, HttpStatus.CREATED);			
	}
	

}
