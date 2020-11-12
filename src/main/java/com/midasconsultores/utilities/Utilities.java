package com.midasconsultores.utilities;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utilities {
	
	 public static final DateFormatSymbols formatSymbols = new DateFormatSymbols(new java.util.Locale("es", "ES"));

	 public static final String FORMAT_DATE = "dd/MM/yyyy";
	 
	 public static final String FORMATO_API = "yyyy-MM-dd";
	
	 public static Date stringToDate( String fechaStr, String pattern ){
	        SimpleDateFormat template=null;
	        Date date = null;
	        if (pattern != null){
	            template = new SimpleDateFormat(pattern);
	        }	        
	        try{             
	            date = template.parse( fechaStr );            
	        }catch(java.text.ParseException e){
	            date = null;
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
	        SimpleDateFormat template = null;
	        //Creo el format de la fecha del sistema
	        if (pattern != null){
	            template = new SimpleDateFormat( pattern );
	        }
	        if (fecha != null) {
	            return template.format(fecha).toString();
	        } else {
	            return new String("");
	        }
	    }
	    
		
	
}
