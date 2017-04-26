package com.uhc.quatropatas.repository.helper.agendamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uhc.quatropatas.model.Agendamento;
import com.uhc.quatropatas.repository.filter.AgendamentoFilter;

public interface AgendamentosQueries {

	public Page<Agendamento> filtrar(AgendamentoFilter filtro, Pageable pageable);
	
	//public Agendamento buscarComServicos(Long codigo);
}
