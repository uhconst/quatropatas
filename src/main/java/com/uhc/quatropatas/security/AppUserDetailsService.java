package com.uhc.quatropatas.security;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.uhc.quatropatas.model.Usuario;
import com.uhc.quatropatas.repository.Usuarios;

@Service
public class AppUserDetailsService implements UserDetailsService{

	@Autowired
	private Usuarios usuarios;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<Usuario> usuarioOptional = usuarios.porEmailAtivo(email);
		
		/*
		 * Exceção para caso não encontre o usuário
		 */
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
		
		return new User(usuario.getEmail(), usuario.getSenha(), new HashSet<>());
	}

}
