package com.uhc.quatropatas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Raca;

public interface Racas extends JpaRepository<Raca, Long>{

	public List<Raca> findByNomeContainingIgnoreCase(String nome);
	
}
