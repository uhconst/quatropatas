package com.uhc.quatropatas.repository.helper.animal;

import java.util.List;

import com.uhc.quatropatas.model.Animal;
import com.uhc.quatropatas.repository.filter.AnimalFilter;

public interface AnimalsQueries {

	public List<Animal> filtrar(AnimalFilter filtro);
	
}
