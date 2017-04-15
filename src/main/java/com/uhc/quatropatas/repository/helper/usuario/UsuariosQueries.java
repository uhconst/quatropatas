package com.uhc.quatropatas.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import com.uhc.quatropatas.model.Usuario;
import com.uhc.quatropatas.repository.filter.UsuarioFilter;

public interface UsuariosQueries {

	public Optional<Usuario> porEmailAtivo(String email);
	
	public List<String> permissoes(Usuario usuario);
	
	public List<Usuario> filtrar(UsuarioFilter filtro);
}