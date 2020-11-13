package com.midasconsultores.cliente;

import java.util.Date;
import java.util.List;

import org.springframework.web.util.UriComponentsBuilder;

import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.entities.Noticia;

public interface IClienteApi {
	
	public WraperArticle getPagina( Date fecha, int pagina ) ;

}
