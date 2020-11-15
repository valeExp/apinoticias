package com.midasconsultores.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.midasconsultores.cliente.IClienteApi;
import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.dto.Respuesta;
import com.midasconsultores.exceptions.ValidacionProcesoException;
import com.midasconsultores.models.Fuente;
import com.midasconsultores.models.Noticia;
import com.midasconsultores.models.ParamsBusquedaNoticia;
import com.midasconsultores.services.INoticiaService;
import com.midasconsultores.utilities.Utilities;
import com.midasconsultores.validators.FuenteNoticia;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("api")
@Api(tags = "Noticias")
@Validated
public class NoticiaController {
	
	
	@Autowired
	INoticiaService noticiaService;
	
	@Autowired
	IClienteApi  clienteApi;

	@ApiOperation(value = "Puebla la base de datos local con noticias sobre Coronavirus en Argentina", nickname = "desafio1")
	@GetMapping("/noticias/poblar-base-datos")
	public ResponseEntity<?>  poblarBaseDatos( @ApiParam( name = "fecha", type = "String", value = "formato YYYY-MM-DD",
								 example = "2020-11-10",  required = true) 
							     @RequestParam(required = true )  @DateTimeFormat(pattern = "yyyy-MM-dd") Date  fecha ) {
		
		List<String> errores = new ArrayList<>();		
		String mensaje = "";		
		
		if( !noticiaService.existeCopiaLocalNoticias(fecha ) ) {			
			
			List<Noticia> noticias = clienteApi.getNoticias(fecha);		
			if ( !noticias.isEmpty() ) {
				
				errores.addAll( controlarCopiaLocalFuentes() );
				errores.addAll( noticiaService.saveNoticias(noticias) );	
				mensaje = "Se registraron las noticias " + ( errores.isEmpty()? "sin errores":"con errores" ); 
						   					
			}else {				
				mensaje = "No se encontraron noticias en el api para el dia "; 						   				
			}				
			
		}else {			
			mensaje = "Noticias existentes en base Local";				
		}
		
		Respuesta respuesta = new Respuesta( errores.isEmpty(), mensaje, errores );
		
		return new ResponseEntity<Respuesta>( respuesta, HttpStatus.OK);
				
	}
	
	private List<String> controlarCopiaLocalFuentes() {
		
		List<String> errores = new ArrayList<>();
		
		if( !noticiaService.existeCopiaLocalFuentes() ) {				
			List<Fuente> fuentes =  clienteApi.getFuentes();
			errores = noticiaService.saveFuentes(fuentes); 
		}	
				
		return errores;		
	}
	
		
	
	@GetMapping("/noticias")
	public ResponseEntity<?> getNoticiasConFiltro( 
			@ApiParam( name = "fecha", type = "String", value = "formato YYYY-MM-DD",  example="" , required = false) 
			@RequestParam(required = false )  @DateTimeFormat(pattern = "yyyy-MM-dd") Date  fecha, 
			@ApiParam( name = "titulo", type = "String", value = "Titulo de la noticia", example="",   required = false)
			@RequestParam(required = false ) String titulo,
			@ApiParam( name = "fuente", type = "String", value = "Fuente de la noticia", example="",  required = false)
			@RequestParam(required = false ) String fuente,
			@ApiParam( name = "pagina", value = "pagina", example="1",  required = false)
			@RequestParam(required = false, value = "pagina"  ) Integer  pagina,
			@ApiParam( name = "ordenarByFuente", type = "String", value = "ASC,DESC", example="ASC",  required = false)
			@RequestParam(required = false, defaultValue = "ASC" ) String ordenarByFuente) {
		
		  

			Map<String, Object> condiciones = new HashMap<>();
			
			if( titulo != null ) {
				condiciones.put( ParamsBusquedaNoticia.titulo.name() , titulo );
			}
			
			if( fuente != null ) {
				condiciones.put( ParamsBusquedaNoticia.fuente.name(), fuente );
			}
			
			if( fecha != null ) {
				condiciones.put( ParamsBusquedaNoticia.fecha.name(),fecha );
			}
			
			if( pagina != null ) {
				condiciones.put( ParamsBusquedaNoticia.pagina.name() , pagina );
			}
						
		
			Paginacion<Noticia> noticias = noticiaService.getNoticiasConFiltro( condiciones,  ordenarByFuente );
		
		    return new ResponseEntity<Paginacion<Noticia>>( noticias, HttpStatus.OK);
		
	}	
	
	
}
