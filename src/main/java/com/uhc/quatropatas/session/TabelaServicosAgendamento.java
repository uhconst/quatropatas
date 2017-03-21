package com.uhc.quatropatas.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.uhc.quatropatas.model.AgendamentoServico;
import com.uhc.quatropatas.model.Animal;
import com.uhc.quatropatas.model.Servico;

class TabelaServicosAgendamento {

	private String uuid;
	
	private List<AgendamentoServico> agendamentos = new ArrayList<>();
	
	public TabelaServicosAgendamento(String uuid) {
		this.uuid = uuid;
	}

	/*
	 * Mapeando o array e retornando a soma de todos os valores unitários. 
	 * Reduce é pra isso(retorna um Optional). E se não tiver nada pra somar, retorna ZERO
	 */
	public BigDecimal getValorTotal(){
		return agendamentos.stream()
				.map(AgendamentoServico::getValorUnitario)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}
	
	public void adicionarServico(Servico servico, Animal animal){
		AgendamentoServico agendamentoServico = new AgendamentoServico();
		agendamentoServico.setServico(servico);
		agendamentoServico.setAnimal(animal);
		agendamentoServico.setValorUnitario(servico.getValor());
		
		/*
		 * Usando o 0 para adicionar sempre no inicio da Tabela
		 */
		agendamentos.add(0, agendamentoServico);
	}
	
	public void deletarServico(Servico servico, Animal animal){
		int indice = IntStream.range(0, agendamentos.size())
				.filter((i) -> agendamentos.get(i).getServico().equals(servico) && agendamentos.get(i).getAnimal().equals(animal))
				.findAny().getAsInt();
		/*int indice = IntStream.range(0, agendamentos.size())
				.filter(i -> agendamentos.get(i).getServico().equals(servico))
				.findAny().getAsInt();*/
		/*int indice = IntStream.range(0, agendamentos.size())
				.filter(i -> agendamentos.get(i).getServico().equals(servico))
				.filter(i -> agendamentos.get(i).getAnimal().equals(animal))
				.findAny().getAsInt();*/
		agendamentos.remove(indice);
	}
	
	public int total(){
		return agendamentos.size();
	}

	public List<AgendamentoServico> getAgendamentos() {
		return agendamentos;
	}

	
	public String getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		TabelaServicosAgendamento other = (TabelaServicosAgendamento) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
	
	
}
