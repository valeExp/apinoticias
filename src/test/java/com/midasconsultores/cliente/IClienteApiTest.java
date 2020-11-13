package com.midasconsultores.cliente;


import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.midasconsultores.utilities.Utilities;



@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IClienteApiTest {
	@Autowired
	IClienteApi clienteApi;  

	@Test
	void test() {
		
		System.out.println("Entro");
		
		Date fecha = Utilities.stringToDate("04/11/2020", Utilities.FORMAT_DATE);
		
		String fechaFormatoApi = Utilities.dateToString( fecha, Utilities.FORMATO_API );
		
		System.out.println("fechaFormatoApi: " +fechaFormatoApi );
		
		
		System.out.println("Entro");
		
	}

}
