package com.uhc.quatropatas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Pessoa;

public interface Pessoas extends JpaRepository<Pessoa, Long>{

	public List<Pessoa> findByNomeContainingIgnoreCase(String nome);

	public Optional<Pessoa> findByCpf(String cpf);

	public List<Pessoa> findByNomeStartingWithIgnoreCase(String nome);
	
}