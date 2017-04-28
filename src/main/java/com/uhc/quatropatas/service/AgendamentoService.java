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
		 * Caso a venda esteja cancelada e o usuário tente salvar.
		 * Na view o botão desaparece quanto está cancelada,
		 * mas aqui no Service protege mais caso a view seja
		 * enganada
		 */
		if (agendamento.isSalvarNegado()) {
			throw new RuntimeException("Usuário tentando salvar um agendamento proibido");
		}
		
		/*
		 * Checando se o agendamento é novo para setar a hora de criação
		 */
		if(agendamento.isNova()){
			agendamento.setDataCriacao(LocalDateTime.now());
		}
		else{
			/*
			 * Setando a data de criação manualmente caso seja uma edição.
			 * Pegando a criação que tá salva no banco e setando no objeto
			 * que está sendo editado e será salvo.
			 */
			Agendamento agendamentoExistente = agendamentos.findOne(agendamento.getCodigo());
			agendamento.setDataCriacao(agendamentoExistente.getDataCriacao());
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

	@Transactional
	public void cancelar(Agendamento agendamento) {
		/*
		 * Buscando o agendamento que está sendo cancelado
		 */
		Agendamento agendamentoExistente = agendamentos.findOne(agendamento.getCodigo());

		agendamentoExistente.setStatus(StatusAgendamento.CANCELADO);

		agendamentos.save(agendamentoExistente);
	}

}
