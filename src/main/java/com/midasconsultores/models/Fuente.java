package com.midasconsultores.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="fuentes")
public class Fuente implements Serializable{
	
	@Id
	String id;
	@NotEmpty	
	@Column(length = 30 )
	String nombre;
	@NotEmpty
	@Column(length = 30 )
	String alcance;
		
	public Fuente() {
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAlcance() {
		return alcance;
	}
	public void setAlcance(String alcance) {
		this.alcance = alcance;
	}	
		
	@Override
	public String toString() {
		return "Fuente [id=" + id + ", nombre=" + nombre + ", alcance=" + alcance + "]";
	}


	private static final long serialVersionUID = 1L;
	
}
