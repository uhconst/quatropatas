package com.uhc.quatropatas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Servico;

public interface Servicos extends JpaRepository<Servico, Long>{

	public List<Servico> findByDescricaoContainingIgnoreCase(String descricao);
	
}
