package com.uhc.quatropatas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uhc.quatropatas.model.Servico;
import com.uhc.quatropatas.repository.Servicos;

@Service
public class ServicoService {

	@Autowired
	private Servicos servicos;
	
	@Transactional
	public void salvar(Servico servico) {
		servicos.save(servico);
	}
	
	@Transactional
	public void deletar(Long codigo){
		servicos.delete(codigo);
	}
}
