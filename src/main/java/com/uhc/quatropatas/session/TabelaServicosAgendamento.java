package com.uhc.quatropatas.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.uhc.quatropatas.model.AgendamentoServico;
import com.uhc.quatropatas.model.Animal;
import com.uhc.quatropatas.model.Servico;

@SessionScope
@Component
public class TabelaServicosAgendamento {

	private List<AgendamentoServico> servicos = new ArrayList<>();
	
	/*
	 * Mapeando o array e retornando a soma de todos os valores unitários. 
	 * Reduce é pra isso(retorna um Optional). E se não tiver nada pra somar, retorna ZERO
	 */
	public BigDecimal getValorTotal(){
		return servicos.stream()
				.map(AgendamentoServico::getValorUnitario)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}
	
	public void adicionarServico(Servico servico, Animal animal){
		AgendamentoServico agendamentoServico = new AgendamentoServico();
		agendamentoServico.setServico(servico);
		agendamentoServico.setAnimal(animal);
		agendamentoServico.setValorUnitario(servico.getValor());
		
		servicos.add(agendamentoServico);
	}
	
	public int total(){
		return servicos.size();
	}
}
