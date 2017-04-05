package com.uhc.quatropatas.repository.helper.pessoa;

import java.util.List;

import com.uhc.quatropatas.model.Pessoa;
import com.uhc.quatropatas.repository.filter.PessoaFilter;

public interface PessoasQueries {

	public List<Pessoa> filtrar(PessoaFilter filtro);
	
}
