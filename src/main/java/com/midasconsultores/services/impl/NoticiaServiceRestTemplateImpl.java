package com.midasconsultores.services.impl;

import java.util.Date;
import java.util.List;
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
import com.midasconsultores.cliente.WraperArticle;
import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.entities.Noticia;
import com.midasconsultores.handler.RestTemplateResponseErrorHandler;
import com.midasconsultores.services.INoticiaService;
import com.midasconsultores.mapper.NoticiaMapper;
import com.midasconsultores.repositories.NoticiaDinamicaRepository;
import com.midasconsultores.repositories.NoticiaRepository;


@Service
@Transactional(readOnly = true) 
public class NoticiaServiceRestTemplateImpl implements INoticiaService {
	
	
	String API_KEY = "9c13d90c8bb8450eb77b7cd899f7a940";
	String URL_NOTICIAS = "https://api.jornalia.net/api/v1/articles";
	
	
	private RestTemplate clienteRest;
	
	@Autowired
	NoticiaRepository noticiaRepository;
	
	
	@Autowired 
	NoticiaDinamicaRepository noticiaDinamicaRepository;
	
    @Autowired
    public NoticiaServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
    	clienteRest = restTemplateBuilder
          .errorHandler(new RestTemplateResponseErrorHandler())
          .build();
    }

	
	@Override
	public List<Noticia> findAll() {
		
		//conPaginacion
		
		//https://api.jornalia.net/api/v1/articles?apiKey=f36f0dc2f3204a3c821130384e208604&startDate=2020-11-10&endDate=2020-11-10&search=covid%20coronavirus&categories=ULTIMAS_NOTICIAS,LOCALES,NACIONALES,ECONOMIA,POLITICA,POLICIALES,SOCIEDAD,SALUD&page=8
			
		
		//https://api.jornalia.net/api/v1/articles?apiKey=f36f0dc2f3204a3c821130384e208604&startDate=2020-11-10&endDate=2020-11-10&search=covid coronavirus&categories=ULTIMAS_NOTICIAS,LOCALES,NACIONALES,ECONOMIA,POLITICA,POLICIALES,SOCIEDAD,SALUD
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_NOTICIAS)
		        .queryParam("apiKey", API_KEY)
		        .queryParam("startDate", "2020-11-05")
		        .queryParam("endDate", "2020-11-05")
		        .queryParam("search", "covid coronavirus")
		        .queryParam("categories", "ULTIMAS_NOTICIAS,LOCALES,NACIONALES,ECONOMIA,POLITICA,POLICIALES,SOCIEDAD,SALUD");
		        

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<WraperArticle> response = clienteRest.exchange(
		        builder.toUriString(), 
		        HttpMethod.GET, 
		        entity, 
		        WraperArticle.class);
		
		WraperArticle wraper = response.getBody();
		
		List<Noticia> noticias =  wraper.getArticles().stream()
													  .map(NoticiaMapper::toEntity)
													  .collect( Collectors.toList() );
		
		
		return noticias;
	
	}


	@Override
	@Transactional(readOnly = false) 
	public void save( List<Noticia> noticias ) {		
		noticias.forEach( noti -> noticiaRepository.save(noti) );
	}


	@Override
	public Page<Noticia> findByTituloContaining(String titulo, Pageable pageable) {
		return noticiaRepository.findByTituloContaining(titulo, pageable);
	}


	@Override
	public Paginacion<Noticia> findWithFilter(Date fecha, String fuente, String titulo, int pagina) {
		return noticiaDinamicaRepository.noticiasConFiltro(fuente, fecha, titulo, pagina);
	}


	
}
