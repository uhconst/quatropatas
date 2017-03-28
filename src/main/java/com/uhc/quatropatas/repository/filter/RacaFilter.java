package com.uhc.quatropatas.repository.filter;

import com.uhc.quatropatas.model.TipoEspecie;

public class RacaFilter {
	
	private String nome;

	private TipoEspecie especie;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public TipoEspecie getEspecie() {
		return especie;
	}

	public void setEspecie(TipoEspecie especie) {
		this.especie = especie;
	}
}
