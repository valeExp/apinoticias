package com.midasconsultores.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;




@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class INoticiaServiceTest {
	
	@Autowired
	INoticiaService noticiaService;
		
	@Value("${api.jornalia.apikey}")
	String apiKey;
	
	@Value("${api.jornalia.urlBase}")
	String urlBase;
	
	
	@Test
	void test() {
		//noticiaService.getFuentesDesdeApi();
	}

}
