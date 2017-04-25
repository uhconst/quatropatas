package com.uhc.quatropatas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uhc.quatropatas.model.Servico;
import com.uhc.quatropatas.repository.Servicos;
import com.uhc.quatropatas.service.exception.ImpossivelExcluirEntidadeException;

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

		try{
			servicos.delete(codigo);
			servicos.flush(); //Força o comando no banco
		}
		catch (DataIntegrityViolationException e){
			throw new ImpossivelExcluirEntidadeException("Impossível apagar o serviço! Já foi usado em algum agendamento.");
		}
		
	}
}
