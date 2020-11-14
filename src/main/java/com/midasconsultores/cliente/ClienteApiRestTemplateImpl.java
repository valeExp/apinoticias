package com.midasconsultores.cliente;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.midasconsultores.adapters.NoticiaAdapter;
import com.midasconsultores.handler.RestTemplateResponseErrorHandler;
import com.midasconsultores.models.Categoria;
import com.midasconsultores.models.Fuente;
import com.midasconsultores.models.Noticia;
import com.midasconsultores.utilities.Utilities;

@Component("clienteRest")
public class ClienteApiRestTemplateImpl implements IClienteApi {	
		
	@Value("${api.jornalia.apikey}")
	String apiKey;
	
	@Value("${api.jornalia.urlBase}")
	String urlBase;
		
	private RestTemplate cliente;
	
	
    @Autowired
    public ClienteApiRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
    	   cliente = restTemplateBuilder
          .errorHandler(new RestTemplateResponseErrorHandler())
          .build();
    }
 
    
    public UriComponentsBuilder crearCriterioBusquedaArticles( Date fecha, int pagina  ) {
    	
    	final String URL_NOTICIAS = urlBase.concat("/api/v1/articles");
    	
    	String fechaFormatoApi = Utilities.dateToString( fecha, Utilities.FORMATO_API );
    	
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_NOTICIAS)
		        .queryParam("apiKey", apiKey)
		        .queryParam("startDate", fechaFormatoApi)
		        .queryParam("endDate", fechaFormatoApi)
		        .queryParam("search", "covid coronavirus")
		        .queryParam("categories",  Categoria.todasMenosExcluidasStr( Categoria.INTERNACIONALES ) )
		        .queryParam("page", pagina);
    	
    	return builder;
    	
    }

	
	public PaginacionProvider getPaginaProviders( int pagina  ) {
		
		final String URL_PROVIDERS = urlBase.concat("/api/v1/providers");		
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);	

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_PROVIDERS)
			   .queryParam("apiKey", apiKey)
		       .queryParam("page", pagina);
		
		HttpEntity<PaginacionProvider> response = cliente.exchange(
		        builder.toUriString(), 
		        HttpMethod.GET, 
		        entity, 
		        PaginacionProvider.class);
		
		PaginacionProvider paginacion = response.getBody();
		
		return paginacion;
				
		
	}

	@Override
	public List<Noticia> getNoticias( Date fecha  ) {
		
		List<Noticia> noticias = new ArrayList<>();		
		
		PaginacionArticle pagina = getPaginaArticles( fecha, 1 );		
		noticias.addAll(  articlesToNoticias( pagina.getArticles() ) );
		
		for( int i = 2; i <= pagina.getPages(); i++ ) {			
			pagina = getPaginaArticles(fecha, i );			
			noticias.addAll( articlesToNoticias( pagina.getArticles() ) );
		}
		
		return noticias;
	}
	
	public PaginacionArticle getPaginaArticles( Date fecha, int pagina ) {    	
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);	

		UriComponentsBuilder builder = crearCriterioBusquedaArticles( fecha, pagina );
		
		HttpEntity<PaginacionArticle> response = cliente.exchange(
		        builder.toUriString(), 
		        HttpMethod.GET, 
		        entity, 
		        PaginacionArticle.class);
		
		PaginacionArticle paginacion = response.getBody();
		
		return paginacion;
	
	}

	private List<Noticia> articlesToNoticias( List<Article> articles ){		    	
	   return articles.stream().map( NoticiaAdapter::articleToNoticia ).collect( Collectors.toList() );	    	
	}


	@Override
	public List<Fuente> getFuentes() {
		
		List<Fuente> fuentes = new ArrayList<>();			
		PaginacionProvider pagina =  getPaginaProviders( 1 );
		fuentes.addAll(  providersToFuentes( pagina.getProviders() ) );
		
		for( int i = 2; i <= pagina.getPages(); i++ ) {			
			pagina = getPaginaProviders( i );			
			fuentes.addAll( providersToFuentes( pagina.getProviders() ) );
		}		
		
		return fuentes;
	}
	
	private List<Fuente> providersToFuentes( List<Provider > providers ) {
		 return  providers .stream()
   			 .map( NoticiaAdapter:: providerToFuente )
   			 .collect( Collectors.toList() );
	}

}
