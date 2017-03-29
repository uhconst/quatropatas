package com.uhc.quatropatas.repository.filter;

import com.uhc.quatropatas.model.Pessoa;
import com.uhc.quatropatas.model.Raca;
import com.uhc.quatropatas.model.TipoSexoAnimal;

public class AnimalFilter {
	
	private String nome;

	private Raca raca;
	
	private Pessoa pessoa;
	
	private TipoSexoAnimal sexo;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Raca getRaca() {
		return raca;
	}

	public void setRaca(Raca raca) {
		this.raca = raca;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public TipoSexoAnimal getSexo() {
		return sexo;
	}

	public void setSexo(TipoSexoAnimal sexo) {
		this.sexo = sexo;
	}
	
}
