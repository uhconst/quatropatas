package com.uhc.quatropatas.repository.helper.usuario;

import java.util.Optional;

import com.uhc.quatropatas.model.Usuario;

public interface UsuariosQueries {

	public Optional<Usuario> porEmailAtivo(String email);
	
}