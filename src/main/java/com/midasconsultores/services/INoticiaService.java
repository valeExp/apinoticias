package com.midasconsultores.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.entities.Noticia;

public interface INoticiaService {

	public List<Noticia> findAll();
	public void save( List<Noticia> noticias );
	public Page<Noticia> findByTituloContaining( String titulo, Pageable pageable );
	public Paginacion<Noticia> findWithFilter( Date fecha, String fuente, String titulo, int pagina );
	
}
