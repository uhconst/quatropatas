package com.uhc.quatropatas.repository.filter;

public class PessoaFilter {
	
	private String nome;

	private String cpf;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		if(cpf!=null){
			return cpf.replaceAll("\\.|-", "");
		}
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}