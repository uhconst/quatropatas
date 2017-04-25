package com.uhc.quatropatas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uhc.quatropatas.model.Raca;
import com.uhc.quatropatas.repository.Racas;
import com.uhc.quatropatas.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class RacaService {

	@Autowired
	private Racas racas;
	
	@Transactional
	public void salvar(Raca raca) {
		racas.save(raca);
	}
	
	@Transactional
	public void deletar(Long codigo){
		try{
			racas.delete(codigo);
			racas.flush();
		}
		catch (DataIntegrityViolationException e){
			throw new ImpossivelExcluirEntidadeException("Impossível apagar a raça! Já foi usada em algum cadastro de animal.");
		}
	}
}
