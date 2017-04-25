package com.uhc.quatropatas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uhc.quatropatas.model.Cidade;
import com.uhc.quatropatas.repository.Cidades;
import com.uhc.quatropatas.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CidadeService {

	@Autowired
	private Cidades cidades;
	
	@Transactional
	public void salvar(Cidade cidade) {
		cidades.save(cidade);
	}
	
	@Transactional
	public void deletar(Long codigo){
		try{
			cidades.delete(codigo);
			cidades.flush();
		}
		catch (DataIntegrityViolationException e){
			throw new ImpossivelExcluirEntidadeException("Impossível apagar o cidade! Já foi cadastrada em algum cliente.");
		}
	}
}
