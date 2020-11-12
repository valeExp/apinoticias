package com.midasconsultores.dto;

import java.util.HashMap;
import java.util.Map;

public class Error {

	String timestamp;
	String status;
	String error;
	String path;
	String mensaje;
	Map<String, String> errors;

	public Error() {
		this.timestamp = "";
		this.status="";
		this.error="";
		this.path="";
		this.mensaje="";
		this.errors = new HashMap<>();	
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

}
