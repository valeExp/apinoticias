package com.midasconsultores.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

@Entity
@Table( name="noticias" )
public class Noticia implements Serializable {

	@Id
	@Column( length = 30 )
	private String id;	
	
	@Column( length = 40 )	
	@NotEmpty
	private String categoria;
	
	@Column( length = 200 )	
	@NotEmpty
	private String titulo;
	
	@Column( length = 200 )
	private String urlNoticia;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaPublicacion;
	
	@OneToOne( fetch = FetchType.EAGER)
	@JoinColumn(name = "fuente_id", nullable = false)
	Fuente fuente; 

	public Noticia() {
		this.id="";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUrlNoticia() {
		return urlNoticia;
	}

	public void setUrlNoticia(String urlNoticia) {
		this.urlNoticia = urlNoticia;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	
	
	public Fuente getFuente() {
		return fuente;
	}

	public void setFuente(Fuente fuente) {
		this.fuente = fuente;
	}

    
	@Override
	public String toString() {
		return "Noticia [id=" + id + ", categoria=" + categoria + ", titulo=" + titulo + ", urlNoticia=" + urlNoticia
				+ ", fechaPublicacion=" + fechaPublicacion + ", fuente=" + fuente + "]";
	}


	private static final long serialVersionUID = 1L;


}
