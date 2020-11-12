package com.midasconsultores.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.entities.Noticia;
import com.midasconsultores.services.INoticiaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("api")
@Api(tags = "Noticias")
public class NoticiaController {
	
	
	@Autowired
	INoticiaService noticiaService;

	@ApiOperation(value = "Lista todas las Noticias referente a covid-19 en Arg por fecha", nickname = "desafio1")
	@GetMapping("/noticias/poblar-base-datos")
	public ResponseEntity<?> getNoticias( @ApiParam( name = "fecha", type = "String", value = "formato YYYY-MM-DD",
										example = "2020-11-10",  required = true) 
							            @RequestParam(required = true ) String fecha ) {
		
		
		List<Noticia> noticias = noticiaService.findAll();		
		noticiaService.save(noticias);
		
		return new ResponseEntity<List<Noticia>>( noticias, HttpStatus.OK );
	}
	
	
	/*@GetMapping("/noticias")
	public ResponseEntity<?> getNoticiasConFiltro( 
			@ApiParam( name = "fecha", type = "String", value = "formato YYYY-MM-DD",  example="" , required = false) 
			@RequestParam(required = false )  @DateTimeFormat(pattern = "yyyy-MM-dd") Date  fecha, 
			@ApiParam( name = "titulo", type = "String", value = "Titulo de la noticia", example="",   required = false)
			@RequestParam(required = false ) String titulo,
			@ApiParam( name = "fuente", type = "String", value = "Fuente de la noticia", example="",  required = false)
			@RequestParam(required = false ) String fuente, Pageable pageable ) {
		
			System.out.println( "fecha:" + fecha + " titulo:" + titulo + " fuente:" + fuente );
		
			Page<Noticia> pageableNotis = noticiaService.findByTituloContaining(titulo, pageable);
		
		   return new ResponseEntity<Page<Noticia>>( pageableNotis, HttpStatus.OK);
		
	}*/
	
	
	@GetMapping("/noticias")
	public ResponseEntity<?> getNoticiasConFiltro( 
			@ApiParam( name = "fecha", type = "String", value = "formato YYYY-MM-DD",  example="" , required = false) 
			@RequestParam(required = false )  @DateTimeFormat(pattern = "yyyy-MM-dd") Date  fecha, 
			@ApiParam( name = "titulo", type = "String", value = "Titulo de la noticia", example="",   required = false)
			@RequestParam(required = false ) String titulo,
			@ApiParam( name = "fuente", type = "String", value = "Fuente de la noticia", example="",  required = false)
			@RequestParam(required = false ) String fuente,
			@RequestParam(required = false ) Integer pagina ) {
			
			
			Paginacion<Noticia> noticias = noticiaService.findWithFilter( fecha, fuente, titulo, pagina!=null?pagina:1 );
		
		   return new ResponseEntity<Paginacion<Noticia>>( noticias, HttpStatus.OK);
		
	}
	
}
