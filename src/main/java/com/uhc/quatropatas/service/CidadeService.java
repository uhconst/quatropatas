package com.uhc.quatropatas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uhc.quatropatas.model.Cidade;
import com.uhc.quatropatas.repository.Cidades;

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
		cidades.delete(codigo);
	}
}
