package com.uhc.quatropatas.repository.helper.cidade;

import java.util.List;

import com.uhc.quatropatas.model.Cidade;
import com.uhc.quatropatas.repository.filter.CidadeFilter;

public interface CidadesQueries {
	public List<Cidade> filtrar(CidadeFilter filtro);
}
