package com.midasconsultores.repositories;

import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.models.Noticia;


public interface NoticiaRepository extends JpaRepository<Noticia,String>{
		
	public Long countByFechaPublicacionBetween( Date fechaInicio, Date fechaFin ); 	
	public Paginacion<Noticia> getNoticiasConFiltro( Map<String, Object> condiciones, boolean ordenarByFuenteAsc );
}
