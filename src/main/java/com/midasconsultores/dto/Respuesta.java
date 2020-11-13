package com.midasconsultores.dto;

public class Respuesta {
	boolean ok;
	String mensaje;

	public Respuesta() {
	}

	public Respuesta(boolean ok, String mensaje) {
		super();
		this.ok = ok;
		this.mensaje = mensaje;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
