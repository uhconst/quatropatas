package com.uhc.quatropatas.repository.filter;

import java.math.BigDecimal;

public class ServicoFilter {

	private String descricao;
	
	private Integer duracaoDe;
	
	private Integer duracaoAte;
	
	private BigDecimal valorDe;
	
	private BigDecimal valorAte;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getDuracaoDe() {
		return duracaoDe;
	}

	public void setDuracaoDe(Integer duracaoDe) {
		this.duracaoDe = duracaoDe;
	}

	public Integer getDuracaoAte() {
		return duracaoAte;
	}

	public void setDuracaoAte(Integer duracaoAte) {
		this.duracaoAte = duracaoAte;
	}

	public BigDecimal getValorDe() {
		return valorDe;
	}

	public void setValorDe(BigDecimal valorDe) {
		this.valorDe = valorDe;
	}

	public BigDecimal getValorAte() {
		return valorAte;
	}

	public void setValorAte(BigDecimal valorAte) {
		this.valorAte = valorAte;
	}
	
}
