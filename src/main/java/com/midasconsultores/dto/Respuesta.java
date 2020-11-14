package com.midasconsultores.dto;

import java.util.ArrayList;
import java.util.List;

public class Respuesta {
	boolean ok;
	String mensaje;
	List<String> errores = new ArrayList<>();

	public Respuesta() {
	}

	public Respuesta(boolean ok, String mensaje, List<String> errores) {
		super();
		this.ok = ok;
		this.mensaje = mensaje;
		this.errores = errores;
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

	public List<String> getErrores() {
		return errores;
	}

	public void setErrores(List<String> errores) {
		this.errores = errores;
	}

}
