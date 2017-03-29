package com.uhc.quatropatas.repository.helper.servico;

import java.util.List;

import com.uhc.quatropatas.model.Servico;
import com.uhc.quatropatas.repository.filter.ServicoFilter;

public interface ServicosQueries {

	public List<Servico> filtrar(ServicoFilter filtro);
	
}
