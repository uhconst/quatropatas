package com.uhc.quatropatas.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uhc.quatropatas.model.Agendamento;
import com.uhc.quatropatas.model.StatusAgendamento;
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
	
		
		if(agendamento.getDataAgendamento() != null){
			agendamento.setDataHoraAgendamento(LocalDateTime.of(agendamento.getDataAgendamento(), agendamento.getHorarioAgendamento()));
		}
		
		agendamentos.save(agendamento);
	}

	@Transactional
	public void agendar(Agendamento agendamento) {
		agendamento.setStatus(StatusAgendamento.AGENDADO);
		salvar(agendamento);
	}

	/*
	@Transactional
	public void deletar(Long codigo){
		agendamentos.delete(codigo);
	}
	*/
}
