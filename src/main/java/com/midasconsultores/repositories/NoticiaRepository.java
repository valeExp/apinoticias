package com.midasconsultores.repositories;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.models.Noticia;


public interface NoticiaRepository extends JpaRepository<Noticia,String>{
		
	public Long countByFechaPublicacionBetween( Date fechaInicio, Date fechaFin ); 	
	public Paginacion<Noticia> getNoticiasConFiltro( Map<String, Object> condiciones, String ordenarByFuente );
	
	@Query( "Select n from Noticia n where n.fechaPublicacion <= :fecha" )
	public List<Noticia> getNoticiasByFechaPublicacion( @Param("fecha") Date fecha );
	
}
