package com.uhc.quatropatas.repository.filter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import com.uhc.quatropatas.model.StatusAgendamento;

public class AgendamentoFilter {

	private Long codigo;
	
	private StatusAgendamento status;

	private LocalDate desdeAgendamento;
	
	private LocalDate ateAgendamento;
	
	private LocalDate desdeCriacao;
	
	private LocalDate ateCriacao;
	
	private BigDecimal valorMinimo;
	
	private BigDecimal valorMaximo;

	private String nomePessoa;
	
	private String cpfPessoa;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public StatusAgendamento getStatus() {
		return status;
	}

	public void setStatus(StatusAgendamento status) {
		this.status = status;
	}
	
	public LocalDate getDesdeAgendamento() {
		return desdeAgendamento;
	}

	public void setDesdeAgendamento(LocalDate desdeAgendamento) {
		this.desdeAgendamento = desdeAgendamento;
	}

	public LocalDate getAteAgendamento() {
		return ateAgendamento;
	}

	public void setAteAgendamento(LocalDate ateAgendamento) {
		this.ateAgendamento = ateAgendamento;
	}

	public LocalDate getDesdeCriacao() {
		return desdeCriacao;
	}

	public void setDesdeCriacao(LocalDate desdeCriacao) {
		this.desdeCriacao = desdeCriacao;
	}

	public LocalDate getAteCriacao() {
		return ateCriacao;
	}

	public void setAteCriacao(LocalDate ateCriacao) {
		this.ateCriacao = ateCriacao;
	}

	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public BigDecimal getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(BigDecimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getCpfPessoa() {
		if(cpfPessoa!=null){
			return cpfPessoa.replaceAll("\\.|-", "");
		}
		return cpfPessoa;
	}

	public void setCpfPessoa(String cpfPessoa) {
		this.cpfPessoa = cpfPessoa;
	}
	
}
