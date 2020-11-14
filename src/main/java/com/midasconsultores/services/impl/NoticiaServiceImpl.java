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
import org.springframework.data.util.MethodInvocationRecorder.Recorded.ToMapConverter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.midasconsultores.adapters.FuenteAdapter;
import com.midasconsultores.adapters.NoticiaAdapter;
import com.midasconsultores.cliente.Article;
import com.midasconsultores.cliente.IClienteApi;
import com.midasconsultores.cliente.PaginacionArticle;
import com.midasconsultores.cliente.PaginacionProvider;
import com.midasconsultores.cliente.Provider;
import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.exceptions.ValidacionProcesoException;
import com.midasconsultores.handler.RestTemplateResponseErrorHandler;
import com.midasconsultores.models.Fuente;
import com.midasconsultores.models.Noticia;
import com.midasconsultores.services.INoticiaService;
import com.midasconsultores.repositories.NoticiaRepositoryImpl;
import com.midasconsultores.repositories.FuenteRepository;
import com.midasconsultores.repositories.NoticiaRepository;


@Service
@Transactional(readOnly = true) 
public class NoticiaServiceImpl implements INoticiaService {
	
		
	@Autowired 
	NoticiaRepository noticiaRepository; 
	
	@Autowired 
	FuenteRepository fuenteRepository;
	
	@Autowired
	IClienteApi clienteApi;


	@Override
	@Transactional(readOnly = false) 
	public void save( List<Noticia> noticias ) {		
		noticias.forEach( noti -> noticiaRepository.save(noti) );
	}	


	@Override
	@Transactional(readOnly = false) 
	public List<Noticia> getNoticiasDesdeApi( Date fecha ) {
		
		List<Noticia> noticias = new ArrayList<>();
		
		//Corrobora si no existen fuentes las trae y alamacena
		if( getFuentesDesdeApi() ) {						
			
			PaginacionArticle pagina = clienteApi.getPaginaArticles( fecha, 1 );		
			noticias.addAll(  articlesToNoticias( pagina.getArticles() ) );
			
			for( int i = 2; i <= pagina.getPages(); i++ ) {			
				pagina = clienteApi.getPaginaArticles(fecha, i );			
				noticias.addAll( articlesToNoticias( pagina.getArticles() ) );
			}
		}else {
			throw new ValidacionProcesoException("No existen Fuentes. No podran grabarse las noticias");
		}		
		
		return noticias;		
	}

    private List<Noticia> articlesToNoticias( List<Article> articles ){    	
    	
    	Map<String, Fuente> fuentes = getFuentes().stream()
				  .collect(  Collectors.toMap(Fuente::getNombre, f -> f ) ); 

    	List<Noticia> noticias = new ArrayList<>();
    	
    	articles.forEach( article ->{
    		Noticia noti =  NoticiaAdapter.articleToNoticia(article);
    		noti.setFuente( fuentes.get(article.getProvider().getName()));
    		noticias.add(noti);
    	});
    	
    	return noticias;
    }


	@Override
	public boolean existeCopiaLocal(Date fecha) {
		Long cantidadRegistro = noticiaRepository.countByFecha(fecha);
		return cantidadRegistro != null && cantidadRegistro > 0;
	}


	@Override
	public Paginacion<Noticia> getNoticiasConFiltro(Map<String, Object> condiciones, boolean ordenFuenteAsc) {
		return noticiaRepository.getNoticiasConFiltro( condiciones,  ordenFuenteAsc);	
	}


	@Override
	@Transactional(readOnly = false ) 
	public boolean getFuentesDesdeApi() {
		
		long cantidadRegistros = fuenteRepository.count(); 
		
		if( cantidadRegistros == 0 ) {
			
			List<Fuente> fuentes = new ArrayList<>();			
			PaginacionProvider pagina =  clienteApi.getPaginaProviders( 1 );
			fuentes.addAll(  providersToFuentes( pagina.getProviders() ) );
			
			for( int i = 2; i <= pagina.getPages(); i++ ) {			
				pagina = clienteApi.getPaginaProviders( i );			
				fuentes.addAll( providersToFuentes( pagina.getProviders() ) );
			}			
						
			cantidadRegistros = fuenteRepository.saveAll(fuentes).size();
		}	
				
		return fuenteRepository.count() > 0;		
	}
	
	private List<Fuente> providersToFuentes( List<Provider > providers ) {
		 return  providers .stream()
    			 .map( FuenteAdapter:: providerToFuente )
    			 .collect( Collectors.toList() );
	}
	
	private List<Fuente> getFuentes() {
		return fuenteRepository.findAll();
	}
	
}
