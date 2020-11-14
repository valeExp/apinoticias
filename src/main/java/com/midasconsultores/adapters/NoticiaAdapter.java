package com.midasconsultores.adapters;

import com.midasconsultores.cliente.Article;
import com.midasconsultores.cliente.Provider;
import com.midasconsultores.models.Fuente;
import com.midasconsultores.models.Noticia;
import com.midasconsultores.utilities.Utilities;

public class NoticiaAdapter {
	
	public static Noticia articleToNoticia( Article article ){
		 
		 Noticia noticia = new Noticia();		 
		 noticia.setId(  article.get_id() );
		 noticia.setCategoria( article.getCategory()  );
		 noticia.setTitulo(  article.getTitle() );
		 noticia.setUrlNoticia( article.getSourceUrl() );
		 noticia.setUrlimagen( article.getImageUrl()  );		 
		 noticia.setFuente( providerToFuente( article.getProvider() )  );		 
		 noticia.setFechaPublicacion( Utilities.stringToDate( article.getPublishedAt(), Utilities.FORMATO_ISO ) );
		 
		 return noticia; 
	 }
	
	 public static Fuente providerToFuente( Provider provider ) {
		
		Fuente fuente = new Fuente();
		fuente.setId( provider.get_id() );
		fuente.setNombre( provider.getName() );
		fuente.setAlcance( provider.getScope() );
		
		return fuente;
	}

}
