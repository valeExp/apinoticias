package com.midasconsultores.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.midasconsultores.models.LogNoticias;
import com.midasconsultores.repositories.LogNoticiasRepository;
import com.midasconsultores.services.ILogNoticiasService;

@Service
@Transactional( readOnly = true )
public class LogNoticiasServiceImpl implements ILogNoticiasService {

	
	@Autowired
	LogNoticiasRepository logNoticiasRepository;
	
	@Override
	@Transactional( readOnly = false )
	public void save(LogNoticias log) {
		logNoticiasRepository.save(log);		
	}
	
	

}
