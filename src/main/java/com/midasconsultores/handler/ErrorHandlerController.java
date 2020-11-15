package com.midasconsultores.handler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.midasconsultores.exceptions.NotFoundException;
import com.midasconsultores.exceptions.ValidacionParametrosException;
import com.midasconsultores.exceptions.ValidacionProcesoException;
import com.midasconsultores.dto.Error;

@ControllerAdvice
public class ErrorHandlerController {

	@ExceptionHandler( org.springframework.dao.DataAccessException.class)
	public ResponseEntity<?> handleDataAccess(HttpServletRequest request, org.springframework.dao.DataAccessException ex) {
		

		Error error = new Error();
		error.setStatus( String.valueOf( HttpStatus.INTERNAL_SERVER_ERROR.value() ) );
		error.setError(  HttpStatus.INTERNAL_SERVER_ERROR.name() );
		error.setPath( request.getRequestURI() );
		error.setTimestamp( LocalDateTime.now().toString() );			
		
		error.setMensaje( "Error base de datos"
						  .concat(ex.getMessage().concat(": ")
						  .concat(ex.getMostSpecificCause().getMessage()) ) );		

		return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	

	@ExceptionHandler	
	public ResponseEntity<?> handleException( HttpServletRequest request, MethodArgumentNotValidException ex ) {	
		
		Error error = new Error();
		error.setStatus( String.valueOf( HttpStatus.BAD_REQUEST.value() ) );
		error.setError(  HttpStatus.BAD_REQUEST.name() );
		error.setPath( request.getRequestURI() );
		error.setTimestamp( LocalDateTime.now().toString() );	
		
		
		Map<String, String> errores = new HashMap<>();
		if ( ex.getBindingResult().hasErrors() ) {
			errores = ex.getBindingResult().getFieldErrors()
							.stream()
							.collect( Collectors.toMap( FieldError::getField , f->f.getDefaultMessage() )  );	
		}		
		
		error.setErrors( errores );
				
		return new ResponseEntity<Error> ( error, HttpStatus.BAD_REQUEST );
	
	}
	
	
	@ExceptionHandler
	public ResponseEntity<?> handleParametrosException( HttpServletRequest request, MissingServletRequestParameterException ex ){	
		
		Error error = new Error();
		error.setStatus( String.valueOf( HttpStatus.BAD_REQUEST.value() ) );
		error.setError(  HttpStatus.BAD_REQUEST.name() );
		error.setPath( request.getRequestURI() );
		error.setTimestamp( LocalDateTime.now().toString() );	
		
		error.getErrors().put(ex.getParameterName(), ex.getMessage() );
		
		  
		return new ResponseEntity<Error> ( error, HttpStatus.BAD_REQUEST ); 
	}
	
	
	@ExceptionHandler
	public ResponseEntity<?> handleParametrosException( HttpServletRequest request, HttpClientErrorException ex ){		
		
		 Error error = new Error();
		 error.setStatus( String.valueOf( HttpStatus.BAD_REQUEST.value() ) );
		 error.setError(  HttpStatus.BAD_REQUEST.name() );
		 error.setPath( request.getRequestURI() );
		 error.setTimestamp( LocalDateTime.now().toString() );		
		 
		
		 Map<String, String> errores = new HashMap<>();
		 errores.put("statusHttpClientError", ex.getStatusCode().toString() );		  
		 errores.put("mensajeHttpClientError", ex.getStatusText() );	
		  
		 error.setErrors( errores );
		 
		  return new ResponseEntity<Error> ( error, HttpStatus.BAD_REQUEST ); 
	}
	
	
	@ExceptionHandler
	public ResponseEntity<?> handleParametrosException( HttpServletRequest request, NotFoundException ex ){		 
		  
		 Error error = new Error();
		 error.setStatus( String.valueOf( HttpStatus.BAD_REQUEST.value() ) );
		 error.setError(  HttpStatus.BAD_REQUEST.name() );
		 error.setPath( request.getRequestURI() );
		 error.setTimestamp( LocalDateTime.now().toString() );	
		 error.setMensaje( "Recurso no encontrado ".concat( ex.getMessage() ) );
					  
		 return new ResponseEntity<Error> ( error, HttpStatus.BAD_REQUEST ); 
	}
	
	
	@ExceptionHandler
	public ResponseEntity<?> handleMismatchException( HttpServletRequest request, MethodArgumentTypeMismatchException ex ){	
		
		  Error error = new Error();
		  error.setStatus( String.valueOf( HttpStatus.BAD_REQUEST.value() ) );
		  error.setError(  HttpStatus.BAD_REQUEST.name() );
		  error.setPath( request.getRequestURI() );
		  error.setTimestamp( LocalDateTime.now().toString() );		
		  
		  if(  ex.getName() != null  ) {
			  error.getErrors().put(  ex.getName(), ex.getMessage()!=null?ex.getMessage():"S/D" );
		  }  
		  
		  return new ResponseEntity<Error> ( error, HttpStatus.BAD_REQUEST ); 
	}
	
	@ExceptionHandler
	public ResponseEntity<?> handleValidacionProcesoException( HttpServletRequest request, ValidacionProcesoException ex ){
		  Error error = new Error();
		  error.setStatus( String.valueOf( HttpStatus.CONFLICT.value() ) );
		  error.setError(  HttpStatus.CONFLICT.name() );
		  error.setPath( request.getRequestURI() );
		  error.setTimestamp( LocalDateTime.now().toString() );		
		  
		  error.setMensaje( ex.getMessage() != null? ex.getMessage() : "S/D" ); 
		  
		  return new ResponseEntity<Error> ( error, HttpStatus.CONFLICT ); 
	}
	
	@ExceptionHandler
	public ResponseEntity<?> handleValidacionProcesoException( HttpServletRequest request, ValidacionParametrosException ex ){
		  Error error = new Error();
		  error.setStatus( String.valueOf( HttpStatus.BAD_REQUEST.value() ) );
		  error.setError(  HttpStatus.BAD_REQUEST.name() );
		  error.setPath( request.getRequestURI() );
		  error.setTimestamp( LocalDateTime.now().toString() );		
		  
		  error.setMensaje( ex.getMessage() != null? ex.getMessage() : "S/D" ); 
		  
		  return new ResponseEntity<Error> ( error, HttpStatus.BAD_REQUEST ); 
	}
	
	
	/*@ExceptionHandler(ConstraintViolationException.class)
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
	    return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	}*/
	
}
