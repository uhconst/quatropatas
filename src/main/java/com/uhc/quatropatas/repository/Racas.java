package com.uhc.quatropatas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Raca;
import com.uhc.quatropatas.repository.helper.raca.RacasQueries;

public interface Racas extends JpaRepository<Raca, Long>, RacasQueries{

	//public List<Raca> findByNomeContainingIgnoreCase(String nome);
	
}