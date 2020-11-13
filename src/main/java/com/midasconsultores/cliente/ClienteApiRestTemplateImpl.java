package com.midasconsultores.cliente;

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
import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.entities.Noticia;
import com.midasconsultores.handler.RestTemplateResponseErrorHandler;
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
    
    @Override
	public WraperArticle getPagina( Date fecha, int pagina ) {    	
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);	

		UriComponentsBuilder builder = crearCriterioBusquedaArticles( fecha, pagina );
		
		HttpEntity<WraperArticle> response = cliente.exchange(
		        builder.toUriString(), 
		        HttpMethod.GET, 
		        entity, 
		        WraperArticle.class);
		
		WraperArticle wraper = response.getBody();
		
		//return NoticiaMapper.toEntity(wraper);	
		
		return wraper;
	
	}
    
    public UriComponentsBuilder crearCriterioBusquedaArticles( Date fecha, int pagina  ) {
    	
    	final String URL_NOTICIAS = urlBase.concat("/api/v1/articles");
    	
    	String fechaFormatoApi = Utilities.dateToString( fecha, Utilities.FORMATO_API );
    	
    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_NOTICIAS)
		        .queryParam("apiKey", apiKey)
		        .queryParam("startDate", fechaFormatoApi)
		        .queryParam("endDate", fechaFormatoApi)
		        .queryParam("search", "covid coronavirus")
		        .queryParam("categories", "ULTIMAS_NOTICIAS,LOCALES,NACIONALES,ECONOMIA,POLITICA,POLICIALES,SOCIEDAD,SALUD")
		        .queryParam("page", pagina);
    	
    	return builder;
    	
    }

}
