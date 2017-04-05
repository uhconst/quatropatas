package com.uhc.quatropatas.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank
	@Size(max=45)
	private String nome;
	
	@NotBlank
	@Size(max=45)
	private String sobrenome;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoSexo sexo;
	
	
	//@DateTimeFormat(pattern = "dd/MM/yyyy")
	//@DateTimeFormat(pattern = "yyyy-dd-MM")
    private LocalDate nascimento;
    
    @CPF
    //@JsonIgnore
    private String cpf;
    
    @Embedded
    @JsonIgnore
    private Endereco endereco;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="codigo_pessoa", referencedColumnName="codigo", nullable = false)
    @JsonIgnore
    private List<Telefone> telefones;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="codigo_pessoa", referencedColumnName="codigo", nullable = false)
    @JsonIgnore
    private List<Email> emails;
    
    @PrePersist @PreUpdate
    private void prePersistPreUpdate(){
    	this.cpf = getCpfSemFormatacao();
    }
    
    @PostLoad
    private void postLoad(){
    	this.cpf = formatarCpf(this.cpf);
    }

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public TipoSexo getSexo() {
		return sexo;
	}

	public void setSexo(TipoSexo sexo) {
		this.sexo = sexo;
	}

	public LocalDate getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@JsonIgnore
	public String getCpfSemFormatacao(){
		return this.cpf.replaceAll("\\.|-", "");
	}
	
	@JsonIgnore
	private String formatarCpf(String cpfSemFormatacao) {
		return cpfSemFormatacao.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
	}
	
	public List<Email> getEmails() {
		return emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}
	
	
	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
/*
	public void adicionaEmail() {
		this.emails.add(email);
		emails[0].setPessoa(this); // mantém a consistência
	} 
*/
}