package com.midasconsultores.cliente;

import java.util.Date;
import java.util.List;

import org.springframework.web.util.UriComponentsBuilder;

import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.models.Noticia;

public interface IClienteApi {
	
	public PaginacionArticle getPaginaArticles( Date fecha, int pagina ) ;
	
	public PaginacionProvider getPaginaProviders( int pagina );

}
