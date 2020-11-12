package com.midasconsultores.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "personas")
public class Persona implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@NotEmpty
	@Size(min=4, max = 20)
	@Column(name = "nombre", length = 20, nullable = false)
	String nombre;	

	@NotEmpty
	@Email
	@Column(name = "email", length = 50, nullable = false, unique = true)
	String email;
	
	
    @Min(1)
	@Max(120)
	@Column(name = "edad", nullable = false)	
	Integer edad;
	
	public Persona() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	private static final long serialVersionUID = 1L;

}
