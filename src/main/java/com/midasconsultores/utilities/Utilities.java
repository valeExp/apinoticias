package com.midasconsultores.utilities;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utilities {
	
	 public static final DateFormatSymbols formatSymbols = new DateFormatSymbols(new java.util.Locale("es", "ES"));
	 public static final String FORMAT_DATE = "dd/MM/yyyy";	 
	 public static final String FORMATO_API = "yyyy-MM-dd";	 
	 public static final String FORMATO_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	
	 public static Date stringToDate( String fechaStr, String pattern ){
	        SimpleDateFormat template=null;
	        Date date = null;
	        if (pattern != null){
	            template = new SimpleDateFormat(pattern);
	            try{             
		            date = template.parse( fechaStr );            
		        }catch(java.text.ParseException e){
		            date = null;
		        }
	        }	        
	        
	        return date;
	 }
	 
	 public static Date fechaMasUnDia( Date fecha ){
			Date fechaMasUno = null;
			
			if( fecha != null ) {
				GregorianCalendar f = new GregorianCalendar();
				f.setTimeInMillis( fecha.getTime() );
				f.add( GregorianCalendar.DATE, 1 );
				fechaMasUno =   f.getTime();
			}
			
			return fechaMasUno;
	}
	 
	 public static String dateToString(java.util.Date fecha, String pattern){
		 	String date = "";
	        SimpleDateFormat template = null;	        
	        if (pattern != null){
	            template = new SimpleDateFormat( pattern );
	            if (fecha != null) {
	            	date =  template.format(fecha).toString();
		        } else {
		        	date = "";
		        }
	        }
	        return date;
	 }	    
		
	 public static Date fechaAddDia( Date fecha, int dias ){
			Date fechaRespuesta = null;
			
			if( fecha != null ) {
				GregorianCalendar f = new GregorianCalendar();
				f.setTimeInMillis( fecha.getTime() );				
				f.add( GregorianCalendar.DATE, dias );			
				fechaRespuesta =   f.getTime();
			}
			
			return fechaRespuesta;
	}
	 
	public static String cortarCadenaALongitud( String cadena, int longitud ){        
        if(  cadena != null && cadena.length() > longitud ){
            cadena = cadena.substring(0, longitud );
        }        
        return cadena;
    }

}
