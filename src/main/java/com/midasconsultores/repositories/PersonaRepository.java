package com.midasconsultores.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.midasconsultores.entities.Persona;

@Repository
public interface PersonaRepository extends PagingAndSortingRepository<Persona, Long> {

}
