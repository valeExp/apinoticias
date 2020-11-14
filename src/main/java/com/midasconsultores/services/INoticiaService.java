package com.midasconsultores.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.models.Noticia;

public interface INoticiaService {

	
	public void save( List<Noticia> noticias );	
	public Paginacion<Noticia> getNoticiasConFiltro( Map<String, Object> condiciones, boolean ordenFuenteAsc );	
	public List<Noticia> getNoticiasDesdeApi( Date fecha );
	public boolean existeCopiaLocal( Date fecha );
	
	public boolean getFuentesDesdeApi();
	
}
