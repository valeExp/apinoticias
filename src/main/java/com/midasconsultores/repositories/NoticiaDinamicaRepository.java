package com.midasconsultores.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.midasconsultores.utilities.Utilities;

@Repository
public class NoticiaDinamicaRepository {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public Paginacion<Noticia> noticiasConFiltro( String fuente, Date fecha, String titulo, int pagina,
												  boolean ordenFuenteAsc ) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Noticia> consultaQuery = cb.createQuery(Noticia.class);
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);

		Root<Noticia> noticias = consultaQuery.from(Noticia.class);

		List<Predicate> predicados = new ArrayList<>();

		if (fuente != null && fuente.length() > 0) {
			predicados.add(cb.equal(noticias.get("fuente"), fuente));
		}

		if (fecha != null) {
			Date fechaMasUno = Utilities.fechaMasUnDia(fecha);
			predicados.add(cb.between(noticias.get("fechaPublicacion"), fecha, fechaMasUno));
		}

		if (titulo != null) {
			predicados.add(cb.like(noticias.get("titulo"), "%" + titulo + "%"));
		}

		if (predicados.isEmpty()) {
			countQuery.select(cb.count(countQuery.from(Noticia.class)));
			consultaQuery.select(noticias);			
		} else {
			countQuery.select(cb.count(noticias)).where(predicados.toArray(new Predicate[predicados.size()]));
			consultaQuery.select(noticias).where(predicados.toArray(new Predicate[predicados.size()]));			
		}
		
		consultaQuery.orderBy( ordenFuenteAsc? cb.asc(noticias.get("fuente")):cb.desc(noticias.get("fuente")) );

		Long total = em.createQuery(countQuery).getSingleResult();

		if (pagina <= 0) {
			pagina = 1;
		}
		int pageSize = 50;
		int pageNumber = (pagina - 1) * pageSize;

		TypedQuery<Noticia> typedQuery = em.createQuery(consultaQuery);
		typedQuery.setFirstResult((pageNumber) * pageSize);
		typedQuery.setMaxResults(pageSize);

		return new Paginacion<Noticia>(pagina, total, typedQuery.getResultList(), pageSize);

	}
	

}
