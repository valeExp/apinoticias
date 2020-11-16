package com.midasconsultores.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.models.Fuente;
import com.midasconsultores.models.Noticia;

public interface INoticiaService {
	
	public List<String> saveNoticias( List<Noticia> noticias );	
	public Paginacion<Noticia> getNoticiasConFiltro( Map<String, Object> condiciones,  String ordenarByFuente );	
	public boolean existeCopiaLocalNoticias( Date fecha );
	public List<Noticia> getNoticiasByFechaPublicacion( Date fecha );
	public void eliminarNoticia( Noticia noti );
	
	
	public boolean existeCopiaLocalFuentes();	
	public List<String> saveFuentes( List<Fuente> fuentes ); 
}
