package com.uhc.quatropatas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Usuario;
import com.uhc.quatropatas.repository.helper.usuario.UsuariosQueries;

public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries{
	
	public Optional<Usuario> findByUsername(String username);

	public List<Usuario> findByCodigoIn(Long[] codigos);

	public Optional<Usuario> findByUsernameOrCodigo(String username, Long codigo);
}
