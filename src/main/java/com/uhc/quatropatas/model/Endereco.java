package com.uhc.quatropatas.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;
	
    //@CEP
    private String cep;
    
    @NotBlank
	@Size(max=45)
    private String bairro;
    
    @NotBlank
	@Size(max=7)
    private String numero;
    
    @NotBlank
	@Size(max=45)
    private String logradouro;
    
    @NotBlank
	@Size(max=45)
    private String complemento;
    
	@ManyToOne
	@JoinColumn(name = "codigo_cidade")
	private Cidade cidade;
	
	@Transient
	private Estado estado;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	
}
