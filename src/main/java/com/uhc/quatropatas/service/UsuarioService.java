package com.uhc.quatropatas.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.uhc.quatropatas.model.Usuario;
import com.uhc.quatropatas.repository.Usuarios;
import com.uhc.quatropatas.service.exception.UsernameUsuarioJaCadastradoException;
import com.uhc.quatropatas.service.exception.ImpossivelExcluirEntidadeException;
import com.uhc.quatropatas.service.exception.SenhaObrigatoriaUsuarioException;

@Service
public class UsuarioService {

	@Autowired
	private Usuarios usuarios;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Transactional
	public void salvar(Usuario usuario) {
		
//		Optional<Usuario> emailUsuarioExistente = usuarios.findByEmail(usuario.getEmail());
		Optional<Usuario> usernameUsuarioExistente = usuarios.findByUsernameOrCodigo(usuario.getUsername(), usuario.getCodigo());

		if(usernameUsuarioExistente.isPresent() && !usernameUsuarioExistente.get().equals(usuario)){
			throw new UsernameUsuarioJaCadastradoException("Username já cadastrado");
		}
		
		if(usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())){
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
		}
		
		/*
		 * Criptografando a senha do usuario antes de salvar no banco.
		 * Como ele Valida duas vezes, no model e antes de salva, setando
		 * a confirmação igual a senha antes de Salvar.
		 */
		if(usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())){
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
		}
		else if(StringUtils.isEmpty(usuario.getSenha())){
			usuario.setSenha(usernameUsuarioExistente.get().getSenha());
		}
		usuario.setConfirmacaoSenha(usuario.getSenha());
		
		usuarios.save(usuario);
	}
	
	@Transactional
	public void deletar(Long codigo){
		try{
			usuarios.delete(codigo);
			usuarios.flush();
		}
		catch (DataIntegrityViolationException e){
			throw new ImpossivelExcluirEntidadeException("Impossível apagar o usuário! Já realizou algum agendamento.");
		}
	}

	@Transactional
	public void alterarStatus(Long[] codigos, StatusUsuario statusUsuario) {
		statusUsuario.executar(codigos, usuarios);
	}
}

