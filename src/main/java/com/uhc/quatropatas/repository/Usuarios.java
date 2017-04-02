package com.uhc.quatropatas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Usuario;
import com.uhc.quatropatas.repository.helper.usuario.UsuariosQueries;

public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries{
	
	public List<Usuario> findByNomeContainingIgnoreCase(String nome);
	
	public Optional<Usuario> findByEmail(String email);
}
