package com.uhc.quatropatas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Estado;

public interface Estados extends JpaRepository<Estado, Long> {
	
	public List<Estado> findByNomeContainingIgnoreCase(String nome);
	
}
