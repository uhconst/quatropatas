package com.uhc.quatropatas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Email;

public interface Emails extends JpaRepository<Email, Long> {

	public List<Email> findByEnderecoContainingIgnoreCase(String nome);
	
}
