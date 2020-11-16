package com.midasconsultores.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.midasconsultores.cliente.IClienteApi;
import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.exceptions.ValidacionParametrosException;
import com.midasconsultores.exceptions.ValidacionProcesoException;
import com.midasconsultores.models.Fuente;
import com.midasconsultores.models.Noticia;
import com.midasconsultores.models.Orden;
import com.midasconsultores.models.ParamsBusquedaNoticia;
import com.midasconsultores.repositories.FuenteRepository;
import com.midasconsultores.repositories.NoticiaRepository;
import com.midasconsultores.services.INoticiaService;
import com.midasconsultores.utilities.Utilities;


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
	public List<String> saveNoticias( List<Noticia> noticias ) {	
		
		List<String> errores = new ArrayList<>();		
		
		Map<String, Fuente> fuentes = getFuentes().stream()
				.collect(  Collectors.toMap(Fuente::getNombre, f -> f ) ); 				
		
		noticias.forEach( noti -> { 
					try {
						//Esto es asi xq el id de la fuente no viene especificado en la noticia  
						noti.setFuente(  fuentes.get( noti.getFuente().getNombre() )  );
						noticiaRepository.save(noti);
					}catch( Exception ex) {
						errores.add("Error en el insert de Fuente: ".concat(ex.getMessage()));				
					}
				} );
		
		return errores;
	}



	@Override
	public boolean existeCopiaLocalNoticias(Date fecha) {
		Long cantidadRegistro = noticiaRepository.countByFechaPublicacionBetween(fecha, Utilities.fechaMasUnDia(fecha));
		return cantidadRegistro != null && cantidadRegistro > 0;
	}


	@Override
	public Paginacion<Noticia> getNoticiasConFiltro( Map<String, Object> condiciones,  String ordenarByFuente ) {
		validarCriteriosBusqueda( condiciones, ordenarByFuente );
		return noticiaRepository.getNoticiasConFiltro( condiciones,  ordenarByFuente);	
	}

	
	private void validarCriteriosBusqueda( Map<String, Object> condiciones, String ordenarByFuente  ) {
	
		if( condiciones.containsKey( ParamsBusquedaNoticia.fuente.name())  ) {
			
			String fuente = (String) condiciones.get( ParamsBusquedaNoticia.fuente.name() );
			
			List<Fuente> fuentes =  getFuentes();			
			
			 getFuentes().stream()
			 .filter( f -> f.getId().equals(fuente) ).findAny()
			 .orElseThrow(  () -> new ValidacionParametrosException( "Los valores permitidos en el campo fuente son:"
					 		.concat( fuentes.stream().map(Fuente::getId).collect( Collectors.joining(",") )   ) ) );
			
		}	
		
		if( !Orden.contiene(ordenarByFuente)) {
			throw new ValidacionParametrosException( "Los valores permitidos en el campo ordenarByFuente son:"
													 .concat( Orden.valoresStr() ) );
		}
		
	}
	
	private List<Fuente> getFuentes() {
		return fuenteRepository.findAll();
	}



	@Override
	public boolean existeCopiaLocalFuentes() {
		return fuenteRepository.count() > 0;
	}



	@Override
	@Transactional(readOnly = false) 
	public List<String> saveFuentes(List<Fuente> fuentes) {
		
		List<String> errores = new ArrayList<>();
		
		fuentes.stream().forEach( fuente ->{
							try {
								fuenteRepository.save(fuente);
							}catch( Exception ex ) {
								errores.add("Error en insert Fuente: ".concat(ex.getMessage()) );
							}} );
		
		return errores;
		
	}


	@Override
	@Transactional(readOnly = true) 
	public List<Noticia> getNoticiasByFechaPublicacion( Date fecha ) {
		return noticiaRepository.getNoticiasByFechaPublicacion( fecha );
	}
	

	@Override
	@Transactional(readOnly = false) 
	public void eliminarNoticia( Noticia noti ) {
		 noticiaRepository.delete(noti);		
	}
	
}
