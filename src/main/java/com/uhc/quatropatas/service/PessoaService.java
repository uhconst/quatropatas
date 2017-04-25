package com.uhc.quatropatas.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uhc.quatropatas.service.exception.CpfPessoaJaCadastradoException;
import com.uhc.quatropatas.service.exception.ImpossivelExcluirEntidadeException;
import com.uhc.quatropatas.model.Pessoa;
import com.uhc.quatropatas.repository.Pessoas;

@Service
public class PessoaService {

	@Autowired
	private Pessoas pessoas;
	
	@Transactional
	public void salvar(Pessoa pessoa) {
		
		Optional<Pessoa> pessoaExistente = pessoas.findByCpf(pessoa.getCpfSemFormatacao());

		if(pessoaExistente.isPresent() && !pessoaExistente.get().equals(pessoa)){
			throw new CpfPessoaJaCadastradoException("CPF já cadastrado");
		}
		
		pessoas.save(pessoa);
	}
	
	@Transactional
	public void deletar(Long codigo){
		try{
			pessoas.delete(codigo);
			pessoas.flush();
		}
		catch (DataIntegrityViolationException e){
			throw new ImpossivelExcluirEntidadeException("Impossível apagar a pessoa! Já foi registrado algum animal para ela.");
		}
	}
	
}
