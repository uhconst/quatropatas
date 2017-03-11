package com.uhc.quatropatas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Usuario;

public interface Usuarios extends JpaRepository<Usuario, Long>{
	
	public List<Usuario> findByNomeContainingIgnoreCase(String nome);
	
	public Optional<Usuario> findByEmail(String email);
}
