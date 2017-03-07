package com.uhc.quatropatas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uhc.quatropatas.model.Raca;
import com.uhc.quatropatas.repository.Racas;

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
		racas.delete(codigo);
	}
}
