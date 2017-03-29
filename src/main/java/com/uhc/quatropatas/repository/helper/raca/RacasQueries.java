package com.uhc.quatropatas.repository.helper.raca;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uhc.quatropatas.model.Raca;
import com.uhc.quatropatas.repository.filter.RacaFilter;

public interface RacasQueries {

	public Page<Raca> filtrar(RacaFilter filtro, Pageable pageable);

}
