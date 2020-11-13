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
import com.midasconsultores.entities.Noticia;
import com.midasconsultores.entities.ParamsBusquedaNoticia;
import com.midasconsultores.services.INoticiaService;
import com.midasconsultores.utilities.Utilities;

@Repository
public class NoticiaRepositoryImpl {
	
	public final int pageSize = 5;

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly = true)
	public Paginacion<Noticia> getNoticiasConFiltro( Map<String, Object> condiciones, boolean ordenFuenteAsc ) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Noticia> consultaQuery = cb.createQuery(Noticia.class);	
		Root<Noticia> noticias = consultaQuery.from(Noticia.class);

		List<Predicate> predicados = crearPredicados(  cb, noticias, condiciones );		
		
		if (predicados.isEmpty()) {			
			consultaQuery.select(noticias);			
		} else {
			
			consultaQuery.select(noticias).where(predicados.toArray(new Predicate[predicados.size()]));			
		}
		
		consultaQuery.orderBy( ordenFuenteAsc? cb.asc(noticias.get("fuente").get("id"))
									 :cb.desc(noticias.get("fuente").get("id")) );
		
		int pagina = (Integer)condiciones.get( ParamsBusquedaNoticia.pagina.name()) ;
		
		return new Paginacion<Noticia>( pagina,
				 						calcularTotalRegistros( condiciones ),
				 						getPagina( consultaQuery, pagina ),
										pageSize);
	}
	
	private List<Noticia> getPagina(CriteriaQuery<Noticia> consultaQuery, int pagina ) {
		
		if (pagina <= 0) {
			pagina = 1;
		}
		
		int pageNumber = (pagina - 1) * pageSize;

		TypedQuery<Noticia> typedQuery = em.createQuery(consultaQuery);
		typedQuery.setFirstResult((pageNumber) * pageSize);
		typedQuery.setMaxResults(pageSize);
		
		return  typedQuery.getResultList();
	}
	
	private long calcularTotalRegistros(  Map<String, Object> condiciones ) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();		
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<Noticia> noticias = countQuery.from(Noticia.class);

		List<Predicate> predicados = crearPredicados(  cb, noticias, condiciones );		

		if (predicados.isEmpty()) {
			countQuery.select(cb.count(countQuery.from(Noticia.class)));
						
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

}
