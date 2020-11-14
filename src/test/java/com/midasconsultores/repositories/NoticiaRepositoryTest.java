package com.midasconsultores.repositories;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.midasconsultores.models.Noticia;
import com.midasconsultores.services.INoticiaService;
import com.midasconsultores.utilities.Utilities;




@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoticiaRepositoryTest {
	
	@Autowired
	NoticiaRepository noticiaRepository;
	
	@Autowired
	INoticiaService noticiaService;

	@Test
	void test() {
		
		
		
	}

}
