package com.uhc.quatropatas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "agendamento")
public class Agendamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL)
	private List<AgendamentoServico> agendamentos;
	
	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;
	
	@Column(name = "data_hora_agendamento")
	private LocalDateTime dataHoraAgendamento;
	
	/*
	@ManyToOne
	@JoinColumn(name = "codigo_usuario")
	private Usuario usuario;
	*/
	
	@Column(name = "valor_desconto")
	private BigDecimal valorDesconto;
	
	@Column(name = "valor_total")
	private BigDecimal valorTotal;
	
	private String observacao;
	
	@Enumerated(EnumType.STRING)
	private StatusAgendamento status = StatusAgendamento.ORCAMENTO;

	@Transient
	private String uuid;
	
	@Transient
	private LocalDate dataAgendamento;
	
	@Transient
	private LocalTime horarioAgendamento;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public List<AgendamentoServico> getAgendamentos() {
		return agendamentos;
	}

	public void setAgendamentos(List<AgendamentoServico> agendamentos) {
		this.agendamentos = agendamentos;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataHoraAgendamento() {
		return dataHoraAgendamento;
	}

	public void setDataHoraAgendamento(LocalDateTime dataHoraAgendamento) {
		this.dataHoraAgendamento = dataHoraAgendamento;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public StatusAgendamento getStatus() {
		return status;
	}

	public void setStatus(StatusAgendamento status) {
		this.status = status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public LocalDate getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(LocalDate dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public LocalTime getHorarioAgendamento() {
		return horarioAgendamento;
	}

	public void setHorarioAgendamento(LocalTime horarioAgendamento) {
		this.horarioAgendamento = horarioAgendamento;
	}

	public boolean isNova(){
		return codigo == null;
	}
	
	/*
	 * Para cada agendamento de servicos e animais, dizendo que esse é o agendamento deles
	 */
	public void adicionarServicos(List<AgendamentoServico> agendamentos) {
		this.agendamentos = agendamentos;
		this.agendamentos.forEach(i -> i.setAgendamento(this));
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
		Agendamento other = (Agendamento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
