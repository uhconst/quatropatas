package com.uhc.quatropatas.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.uhc.quatropatas.model.Usuario;
import com.uhc.quatropatas.repository.Usuarios;
import com.uhc.quatropatas.service.exception.EmailUsuarioJaCadastradoException;
import com.uhc.quatropatas.service.exception.SenhaObrigatoriaUsuarioException;

@Service
public class UsuarioService {

	@Autowired
	private Usuarios usuarios;
	
	@Transactional
	public void salvar(Usuario usuario) {
		
		Optional<Usuario> emailUsuarioExistente = usuarios.findByEmail(usuario.getEmail());
		
		if(emailUsuarioExistente.isPresent()){
			throw new EmailUsuarioJaCadastradoException("Email já cadastrado");
		}
		
		if(usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())){
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
		}
		
		usuarios.save(usuario);
	}
	
	@Transactional
	public void deletar(Long codigo){
		usuarios.delete(codigo);
	}
}

