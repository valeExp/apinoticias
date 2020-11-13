package com.midasconsultores.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.midasconsultores.adapters.NoticiaAdapter;
import com.midasconsultores.cliente.Article;
import com.midasconsultores.cliente.IClienteApi;
import com.midasconsultores.cliente.WraperArticle;
import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.entities.Noticia;
import com.midasconsultores.handler.RestTemplateResponseErrorHandler;
import com.midasconsultores.services.INoticiaService;
import com.midasconsultores.repositories.NoticiaRepositoryImpl;
import com.midasconsultores.repositories.NoticiaRepository;


@Service
@Transactional(readOnly = true) 
public class NoticiaServiceImpl implements INoticiaService {
	
	@Autowired 
	NoticiaRepositoryImpl noticiaDinamicaRepository;
	
	@Autowired 
	NoticiaRepository noticiaRepository; 
	
	@Autowired
	IClienteApi clienteApi;


	@Override
	@Transactional(readOnly = false) 
	public void save( List<Noticia> noticias ) {		
		noticias.forEach( noti -> noticiaRepository.save(noti) );
	}	


	@Override
	@Transactional(readOnly = true) 
	public List<Noticia> getNoticiasDesdeApi( Date fecha ) {
		
		List<Noticia> noticias = new ArrayList<>();	
		
		WraperArticle pagina = clienteApi.getPagina(fecha, 1 );		
		noticias.addAll(  articlesToNoticias( pagina.getArticles() ) );
		
		for( int i = 2; i <= pagina.getPages(); i++ ) {			
			pagina = clienteApi.getPagina(fecha, i );			
			noticias.addAll( articlesToNoticias( pagina.getArticles() ) );
		}
		
		return noticias;		
	}

    private List<Noticia> articlesToNoticias( List<Article> articles ){    	
    	 return  articles .stream()
    			 .map(NoticiaAdapter::articleToNoticia)
    			 .collect( Collectors.toList() );
    }


	@Override
	public boolean existeCopiaLocal(Date fecha) {
		Long cantidadRegistro = noticiaRepository.countByFecha(fecha);
		return cantidadRegistro != null && cantidadRegistro > 0;
	}


	@Override
	public Paginacion<Noticia> getNoticiasConFiltro(Map<String, Object> condiciones, boolean ordenFuenteAsc) {
		return noticiaDinamicaRepository.getNoticiasConFiltro( condiciones,  ordenFuenteAsc);	
	}
	
}
