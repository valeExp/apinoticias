package com.midasconsultores.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.midasconsultores.entities.Noticia;




@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoticiaRepositoryTest {
	
	@Autowired
	NoticiaRepository noticiaRepository;

	@Test
	void test() {
		
		Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
		
		Page<Noticia> pageNotis = noticiaRepository.findByTituloContaining("Coronavirus", firstPageWithTwoElements);
		
		System.out.println( pageNotis.getTotalElements() );
		
		pageNotis.toList().forEach( n -> System.out.println( n.getTitulo() ));
		
	}

}
