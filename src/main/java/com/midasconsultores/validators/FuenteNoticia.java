package com.midasconsultores.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import com.midasconsultores.validators.FuenteNoticiaValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;


@Target({ FIELD,PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = FuenteNoticiaValidator.class)
@Documented

public @interface FuenteNoticia {

	  String message() default "{fuentesvalidas.mensajePorDefecto}";;	
	  Class<?>[] groups() default { };
	  Class<? extends Payload>[] payload() default { };
}


