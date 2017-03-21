package com.uhc.quatropatas.session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.uhc.quatropatas.model.AgendamentoServico;
import com.uhc.quatropatas.model.Animal;
import com.uhc.quatropatas.model.Servico;

@SessionScope
@Component
public class TabelasAgendamentosSession {

	private Set<TabelaServicosAgendamento> tabelas = new HashSet<>();

	public void adicionarServico(String uuid, Servico servico, Animal animal) {
		TabelaServicosAgendamento tabela = buscarTabelaPorUuid(uuid);
		tabela.adicionarServico(servico, animal);
		tabelas.add(tabela);
	}

	public void deletarServico(String uuid, Servico servico, Animal animal) {
		TabelaServicosAgendamento tabela = buscarTabelaPorUuid(uuid);
		tabela.deletarServico(servico, animal);
	}

	public List<AgendamentoServico> getAgendamentos(String uuid) {
		return buscarTabelaPorUuid(uuid).getAgendamentos();
	}
	
	private TabelaServicosAgendamento buscarTabelaPorUuid(String uuid) {
		TabelaServicosAgendamento tabela = tabelas.stream()
				.filter(t -> t.getUuid().equals(uuid))
				.findAny()
				.orElse(new TabelaServicosAgendamento(uuid));
		return tabela;
	}

	
}
