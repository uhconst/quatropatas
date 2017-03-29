package com.uhc.quatropatas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Cidade;
import com.uhc.quatropatas.repository.helper.cidade.CidadesQueries;

public interface Cidades extends JpaRepository<Cidade, Long>, CidadesQueries{
	
	//public List<Cidade> findByNomeContainingIgnoreCase(String nome);
	
	/*
	 * Busca pelo código da entidade Estado
	 */
	public List<Cidade> findByEstadoCodigo(Long codigoEstado);
}
