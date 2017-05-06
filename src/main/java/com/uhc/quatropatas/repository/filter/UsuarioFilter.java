package com.uhc.quatropatas.repository.filter;

import java.util.List;

import com.uhc.quatropatas.model.Grupo;

public class UsuarioFilter {
	private String nome;
	
	private String username;
	
	private List<Grupo> grupos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
	
}
