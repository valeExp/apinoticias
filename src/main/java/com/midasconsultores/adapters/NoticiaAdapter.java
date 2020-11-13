package com.midasconsultores.adapters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.midasconsultores.cliente.Article;
import com.midasconsultores.cliente.WraperArticle;
import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.entities.Noticia;

public class NoticiaAdapter {
	
	public static Noticia articleToNoticia( Article article ){
		 
		 Noticia noticia = new Noticia();
		 
		 noticia.setId(  article.get_id() );
		 noticia.setCategoria( article.getCategory()  );
		 //noticia.setFuente( article.getProvider().getName() );
		 noticia.setTitulo(  article.getTitle() );
		 noticia.setUrlNoticia( article.getSourceUrl() );
		 
		 
		 DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");		  
		 Date date = null;
		try {
			date = df1.parse(article.getPublishedAt());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		 
		 noticia.setFechaPublicacion( date );
		 
		 return noticia; 
	 }
	
	
	public static Paginacion<Noticia> wraperArticleTOPaginacion( WraperArticle wraper ) {
		
		List<Noticia> noticias =  wraper.getArticles().stream()
				  .map(NoticiaAdapter::articleToNoticia)
				  .collect( Collectors.toList() );
		
		Paginacion<Noticia> paginacion = new Paginacion<Noticia>( wraper.getPage(),wraper.getPages(), noticias, 50  );		
		
		return paginacion;		
	}
}
