package com.midasconsultores.cliente;

import java.util.Date;
import java.util.List;

import org.springframework.web.util.UriComponentsBuilder;

import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.models.Fuente;
import com.midasconsultores.models.Noticia;

public interface IClienteApi {	
	
	public List<Fuente> getFuentes();	
	public List<Noticia> getNoticias( Date fecha );

}
