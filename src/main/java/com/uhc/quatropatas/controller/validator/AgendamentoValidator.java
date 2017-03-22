package com.uhc.quatropatas.controller.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uhc.quatropatas.model.Agendamento;

@Component
public class AgendamentoValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Agendamento.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//ValidationUtils.rejectIfEmpty(errors, "animal.codigo", "", "Selecione um animal para o agendamento");
		
		Agendamento agendamento = (Agendamento) target;
		
		validarSeInformouDataHorarioAgendamento(errors, agendamento);
		
		validarSeInformouServicoEAnimal(errors, agendamento);
		
		validarValorTotalNegativo(errors, agendamento);
	}

	private void validarValorTotalNegativo(Errors errors, Agendamento agendamento) {
		if(agendamento.getValorTotal().compareTo(BigDecimal.ZERO)<0){
			errors.reject("","Valor total não pode ser negativo");
		}
	}

	private void validarSeInformouServicoEAnimal(Errors errors, Agendamento agendamento) {
		if(agendamento.getAgendamentos().isEmpty()){
			/*
			 * Sem vincular com um campo especifico, apenas mandando a mensagem
			 */
			errors.reject("", "Adicione pelo menos um serviço e animal no agendamento");
		}
	}

	private void validarSeInformouDataHorarioAgendamento(Errors errors, Agendamento agendamento) {
		if(agendamento.getDataAgendamento() == null){
			errors.rejectValue("dataAgendamento", "", "Informe a data que deseja agendar");
		}
		
		if(agendamento.getHorarioAgendamento() == null){
			errors.rejectValue("horarioAgendamento", "", "Informe o horario que deseja agendar");
		}
	}

}
