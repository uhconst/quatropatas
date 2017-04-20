package com.uhc.quatropatas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "email")
public class Email implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank
	@Size(max=45)
	private String endereco;

	@Column(name="codigo_pessoa", insertable = false, updatable = false)
	private Long codigoPessoa;
	
//	@ManyToOne
//	@JoinColumn(name = "codigo_pessoa")
//	private Pessoa codigoPessoa;
	
	//TESTE, PODE APAGAR
    @PrePersist @PreUpdate
    private void prePersistPreUpdate(){
    	System.out.println(">>>>EMAIL - Codigo email pre update: " +  codigo);
    	System.out.println(">>>>EMAIL - Codigo pessoa pre update: " +  codigoPessoa);
    }
    
    //TESTE, PODE APAGAR
    @PostLoad
    private void postLoad(){
    	System.out.println(">>>>EMAIL - Codigo email pos load: " +  codigo);
    	System.out.println(">>>>EMAIL - Codigo pessoa pos load: " +  codigoPessoa);
    }
    
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public long getCodigoPessoa() {
		return codigoPessoa;
	}

	public void setCodigoPessoa(Long codigoPessoa) {
		this.codigoPessoa = codigoPessoa;
	}
	

//	public Pessoa getCodigoPessoa() {
//		return codigoPessoa;
//	}
//
//	public void setCodigoPessoa(Pessoa codigoPessoa) {
//		this.codigoPessoa = codigoPessoa;
//	}

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
		Email other = (Email) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
