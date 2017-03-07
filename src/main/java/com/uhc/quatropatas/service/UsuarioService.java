package com.uhc.quatropatas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uhc.quatropatas.model.Usuario;
import com.uhc.quatropatas.repository.Usuarios;

@Service
public class UsuarioService {

	@Autowired
	private Usuarios usuarios;
	
	@Transactional
	public void salvar(Usuario usuario) {
		usuarios.save(usuario);
	}
	
	@Transactional
	public void deletar(Long codigo){
		usuarios.delete(codigo);
	}
}

