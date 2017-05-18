package com.uhc.quatropatas.repository.filter;

import com.uhc.quatropatas.model.Pessoa;
import com.uhc.quatropatas.model.Raca;
import com.uhc.quatropatas.model.TipoSexoAnimal;

public class AnimalFilter {
	
	private String nome;

	private Raca raca;
	
	private Pessoa pessoa;
	
	private Integer pesoDe;
	
	private Integer pesoAte;
	
	private TipoSexoAnimal sexo;

	private String nomePessoa;
	
	private String cpfPessoa;
	
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

	public Integer getPesoDe() {
		return pesoDe;
	}

	public void setPesoDe(Integer pesoDe) {
		this.pesoDe = pesoDe;
	}

	public Integer getPesoAte() {
		return pesoAte;
	}

	public void setPesoAte(Integer pesoAte) {
		this.pesoAte = pesoAte;
	}

	public TipoSexoAnimal getSexo() {
		return sexo;
	}

	public void setSexo(TipoSexoAnimal sexo) {
		this.sexo = sexo;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getCpfPessoa() {
		return cpfPessoa;
	}

	public void setCpfPessoa(String cpfPessoa) {
		this.cpfPessoa = cpfPessoa;
	}
	
}
