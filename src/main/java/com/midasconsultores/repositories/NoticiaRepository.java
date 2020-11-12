package com.midasconsultores.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.midasconsultores.entities.Noticia;



//public interface NoticiaRepository extends JpaRepository<Noticia,String>{

public interface NoticiaRepository extends PagingAndSortingRepository<Noticia,String>{
	
	Page<Noticia> findByTituloContaining( String titulo, Pageable pageable );

	//findByLastnameContaining("ea")
}
