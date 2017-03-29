package com.uhc.quatropatas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Animal;
import com.uhc.quatropatas.repository.helper.animal.AnimalsQueries;

public interface Animals extends JpaRepository<Animal, Long>, AnimalsQueries{

	//public List<Animal> findByNomeContainingIgnoreCase(String nome);
	
	/*
	 * Busca pelo código da entidade Pessoa
	 */
	public List<Animal> findByPessoaCodigo(Long codigoAnimal);
}