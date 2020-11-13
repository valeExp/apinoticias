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
import com.midasconsultores.dto.Respuesta;
import com.midasconsultores.entities.Noticia;
import com.midasconsultores.services.INoticiaService;
import com.midasconsultores.utilities.Utilities;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("api")
@Api(tags = "Noticias")
public class NoticiaController {
	
	
	@Autowired
	INoticiaService noticiaService;

	@ApiOperation(value = "Puebla la base de datos local con noticias sobre Coronavirus en Argentina", nickname = "desafio1")
	@GetMapping("/noticias/poblar-base-datos")
	public ResponseEntity<?>  poblarBaseDatos( @ApiParam( name = "fecha", type = "String", value = "formato YYYY-MM-DD",
								 example = "2020-11-10",  required = true) 
							     @RequestParam(required = true )  @DateTimeFormat(pattern = "yyyy-MM-dd") Date  fecha ) {
		
		Respuesta respuesta;
		
		if( !noticiaService.existeCopiaLocal(fecha ) ) {
			List<Noticia> noticias = noticiaService.getNoticiasDesdeApi(fecha);		
			if ( !noticias.isEmpty() ) {
				noticiaService.save(noticias);	
				respuesta = new Respuesta(true,"La base se poblo para el dia " 
						   .concat(	Utilities.dateToString(fecha, Utilities.FORMAT_DATE ) ) );	
			}else {
				respuesta = new Respuesta(true,"No se encontraron registros en el api para el dia " 
						   .concat(	Utilities.dateToString(fecha, Utilities.FORMAT_DATE ) ) );	
			}
			
		}else {
			respuesta = new Respuesta(true,"La base ya esta poblada para el dia " 
										   .concat(	Utilities.dateToString(fecha, Utilities.FORMAT_DATE ) ) );			
		}
		
		return new ResponseEntity<Respuesta>( respuesta, HttpStatus.OK);
				
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
			@ApiParam( name = "pagina", type = "String", value = "pagina", example="",  required = false)
			@RequestParam(required = false ) Integer pagina,
			@ApiParam( name = "ordenFuenteAsc", type = "boolean", value = "ordenFuenteAsc", example="",  required = false)
			@RequestParam(required = false ) Boolean ordenFuenteAsc) {
			
			
			Paginacion<Noticia> noticias = noticiaService.findWithFilter( fecha, 
																		  fuente,
																		  titulo, 
																		  pagina!=null?pagina:1,
																		  ordenFuenteAsc!=null?ordenFuenteAsc:true );
		
		   return new ResponseEntity<Paginacion<Noticia>>( noticias, HttpStatus.OK);
		
	}
	
}
