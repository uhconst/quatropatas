package com.uhc.quatropatas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Cidade;

public interface Cidades extends JpaRepository<Cidade, Long>{
	
	public List<Cidade> findByNomeContainingIgnoreCase(String nome);
	
	/*
	 * Busca pelo código da entidade Estado
	 */
	public List<Cidade> findByEstadoCodigo(Long codigoEstado);
}
