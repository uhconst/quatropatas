package com.uhc.quatropatas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Telefone;

public interface Telefones extends JpaRepository<Telefone, Long> {

	public List<Telefone> findByNumeroContainingIgnoreCase(String nome);
	
}
