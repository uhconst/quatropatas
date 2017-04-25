package com.uhc.quatropatas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uhc.quatropatas.model.Animal;
import com.uhc.quatropatas.repository.Animals;
import com.uhc.quatropatas.service.exception.ImpossivelExcluirEntidadeException;

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
		try{
			services.delete(codigo);
			services.flush();
		}
		catch (DataIntegrityViolationException e){
			throw new ImpossivelExcluirEntidadeException("Impossível apagar o animal! Já foi usado em algum agendamento.");
		}
	}
}

