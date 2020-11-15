package com.midasconsultores.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FuenteNoticiaValidator implements ConstraintValidator<FuenteNoticia, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		//return FuentesPermitidas.pertenece( value );
		return true;
	}




}
