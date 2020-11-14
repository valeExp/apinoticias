package com.midasconsultores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.midasconsultores.models.Fuente;

public interface FuenteRepository extends JpaRepository< Fuente, String > {
	
}
