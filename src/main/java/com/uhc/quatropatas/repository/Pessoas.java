package com.uhc.quatropatas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Pessoa;
import com.uhc.quatropatas.repository.helper.pessoa.PessoasQueries;

public interface Pessoas extends JpaRepository<Pessoa, Long>, PessoasQueries{

	public List<Pessoa> findByNomeContainingIgnoreCase(String nome);

	public Optional<Pessoa> findByCpf(String cpf);

	public List<Pessoa> findByNomeStartingWithIgnoreCase(String nome);
	
}