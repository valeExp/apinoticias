package com.midasconsultores.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.midasconsultores.cliente.IClienteApi;
import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.models.Fuente;
import com.midasconsultores.models.Noticia;
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
	public Paginacion<Noticia> getNoticiasConFiltro(Map<String, Object> condiciones, boolean ordenFuenteAsc) {
		return noticiaRepository.getNoticiasConFiltro( condiciones,  ordenFuenteAsc);	
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
	
}
