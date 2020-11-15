package com.midasconsultores.exceptions;

public class ValidacionParametrosException  extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ValidacionParametrosException( String mensaje ) {
		super(mensaje);		
	}
}
