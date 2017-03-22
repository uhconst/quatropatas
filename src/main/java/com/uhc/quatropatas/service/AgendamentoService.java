package com.uhc.quatropatas.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uhc.quatropatas.model.Agendamento;
import com.uhc.quatropatas.model.AgendamentoServico;
import com.uhc.quatropatas.repository.Agendamentos;

@Service
public class AgendamentoService {

	@Autowired
	private Agendamentos agendamentos;
	
	@Transactional
	public void salvar(Agendamento agendamento) {
		/*
		 * Checando se o agendamento é novo para setar a hora de criação
		 */
		if(agendamento.isNova()){
			agendamento.setDataCriacao(LocalDateTime.now());
		}
		
		BigDecimal valorTotalServicos = agendamento.getAgendamentos().stream()
				.map(AgendamentoServico::getValorUnitario)
				.reduce(BigDecimal::add)
				.get();
		BigDecimal valorTotal = calcularValorTotal(valorTotalServicos, agendamento.getValorDesconto());
		agendamento.setValorTotal(valorTotal);
		
		if(agendamento.getDataAgendamento() != null){
			agendamento.setDataHoraAgendamento(LocalDateTime.of(agendamento.getDataAgendamento(), agendamento.getHorarioAgendamento()));
		}
		
		agendamentos.save(agendamento);
	}

	private BigDecimal calcularValorTotal(BigDecimal valorTotalServicos, BigDecimal valorDesconto) {
		BigDecimal valorTotal = valorTotalServicos
				.subtract(Optional.ofNullable(valorDesconto).orElse(BigDecimal.ZERO));
		return valorTotal;
	}
	
	/*
	@Transactional
	public void deletar(Long codigo){
		agendamentos.delete(codigo);
	}
	*/
}
