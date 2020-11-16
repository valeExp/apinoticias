package com.midasconsultores.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.midasconsultores.dto.Paginacion;
import com.midasconsultores.models.Noticia;
import com.midasconsultores.models.Orden;
import com.midasconsultores.models.ParamsBusquedaNoticia;
import com.midasconsultores.services.INoticiaService;
import com.midasconsultores.utilities.Utilities;

@Repository
public class NoticiaRepositoryImpl {
	
	public static final int pageSize = 50;

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly = true)
	public Paginacion<Noticia> getNoticiasConFiltro( Map<String, Object> condiciones, String ordenarByFuente ) {	
				
		long total = calcularTotalRegistros( condiciones );
		int pages =  calcularTotalPages( total );
		int page = ( (Integer)condiciones.get( ParamsBusquedaNoticia.pagina.name()) );		
		page = ( page > pages ) ? pages:page;
		page = ( page <= 0 )?1:page;
		
		List<Noticia> noticias = new ArrayList<>();		
		if( total > 0 ) {			
			noticias = getPagina( crearQueryNoticias( condiciones, ordenarByFuente ), page ); 
		}		
		
		return new Paginacion<Noticia>( page, pages, total,	ordenarByFuente, noticias );
										
	}
	
	private CriteriaQuery<Noticia> crearQueryNoticias( Map<String, Object> condiciones, String ordenarByFuente ) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Noticia> consultaQuery = cb.createQuery(Noticia.class);	
		Root<Noticia> noticias = consultaQuery.from(Noticia.class);

		List<Predicate> predicados = crearPredicados(  cb, noticias, condiciones );		
		
		if (predicados.isEmpty()) {			
			consultaQuery.select(noticias);			
		} else {
			
			consultaQuery.select(noticias).where(predicados.toArray(new Predicate[predicados.size()]));			
		}
		
		consultaQuery.orderBy( ordenarByFuente.equals(Orden.ASC.name())?
									  cb.asc(noticias.get("fuente").get("id"))
									 :cb.desc(noticias.get("fuente").get("id")) );
		
		return consultaQuery;
		
	}
	
	private List<Noticia> getPagina( CriteriaQuery<Noticia> consultaQuery, int page ) {		

		TypedQuery<Noticia> typedQuery = em.createQuery(consultaQuery);
		typedQuery.setFirstResult( (page - 1) * pageSize );
		typedQuery.setMaxResults(pageSize);
		
		return  typedQuery.getResultList();
	}
	
	public long calcularTotalRegistros(  Map<String, Object> condiciones ) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();		
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<Noticia> noticias = countQuery.from(Noticia.class);

		List<Predicate> predicados = crearPredicados(  cb, noticias, condiciones );		

		if (predicados.isEmpty()) {
			countQuery.select(cb.count(noticias));
						
		} else {
			countQuery.select(cb.count(noticias)).where(predicados.toArray(new Predicate[predicados.size()]));
						
		}		
		
		Long total = em.createQuery(countQuery).getSingleResult();		
		return total;		
	}
	
	
	private List<Predicate> crearPredicados( CriteriaBuilder cb, Root<Noticia> noticias,
											 Map<String, Object> condiciones ) {
		
		List<Predicate> predicados = new ArrayList<>();
		
		if( condiciones.containsKey( ParamsBusquedaNoticia.fuente.name() ) ) {			
			predicados.add(cb.equal( noticias.get("fuente").get("id"), 
									 condiciones.get(ParamsBusquedaNoticia.fuente.name())) );
		}

		if( condiciones.containsKey( ParamsBusquedaNoticia.titulo.name() ) ) {
			predicados.add(cb.like(noticias.get("titulo"), 
									"%" + condiciones.get( ParamsBusquedaNoticia.titulo.name() ) + "%"));
		}	
		
		if( condiciones.containsKey( ParamsBusquedaNoticia.fecha.name() ) ) {
			Date fecha =  (Date)condiciones.get( ParamsBusquedaNoticia.fecha.name() );
			Date fechaMasUno = Utilities.fechaMasUnDia(fecha);
			predicados.add(cb.between(noticias.get("fechaPublicacion"), fecha, fechaMasUno));
		}
		
		return predicados;
	}
	
	private int calcularTotalPages( long total ) {		
		int pages = 0;		
		if( total > 0 ) {
			long resto = total % pageSize;
			
			pages = (resto > 0) ? (int)(total / pageSize) + 1:(int)(total / pageSize);
		}
		return 	pages;	
	}

	
	/*@Override
	@Transactional
	public void delete(Long id) {
		em.remove(findOne(id));
	}*/
	
	
}
