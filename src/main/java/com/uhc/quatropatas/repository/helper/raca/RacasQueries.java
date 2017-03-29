package com.uhc.quatropatas.repository.helper.raca;

import java.util.List;

import com.uhc.quatropatas.model.Raca;
import com.uhc.quatropatas.repository.filter.RacaFilter;

public interface RacasQueries {

	public List<Raca> filtrar(RacaFilter filtro);

}
