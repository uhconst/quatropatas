package com.uhc.quatropatas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uhc.quatropatas.model.Animal;
import com.uhc.quatropatas.repository.Animals;

@Service
public class AnimalService {

	@Autowired
	private Animals services;
	
	@Transactional
	public void salvar(Animal service) {
		services.save(service);
	}
	
	@Transactional
	public void deletar(Long codigo){
		services.delete(codigo);
	}
}

