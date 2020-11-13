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
import com.midasconsultores.entities.Noticia;


public interface NoticiaRepository extends JpaRepository<Noticia,String>{
	
	//Para Oracle
	/*@Query(value = "SELECT count(n.id) FROM Noticia n where trunc( n.fechaPublicacion ) = trunc(:fecha) ")
	public Long countByFecha(@Param(value = "fecha") Date fecha);*/
	
	
	
	@Query(value = "SELECT count(n.id) FROM Noticia n where  CAST(n.fechaPublicacion AS date) =  CAST(:fecha AS date) ")
	public Long countByFecha(@Param(value = "fecha") Date fecha);
	
	
	public Paginacion<Noticia> getNoticiasConFiltro( Map<String, Object> condiciones, boolean ordenFuenteAsc );
}
