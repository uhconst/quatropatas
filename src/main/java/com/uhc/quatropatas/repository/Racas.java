package com.uhc.quatropatas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Raca;
import com.uhc.quatropatas.model.TipoEspecie;
import com.uhc.quatropatas.repository.helper.raca.RacasQueries;

public interface Racas extends JpaRepository<Raca, Long>, RacasQueries{

	/*
	 * Buscar a lista de Racas pela Especie passada como parametro
	 * Ordenando pelo nome da raça, assim na view fica
	 * em ordem crescente pelo nome da raça
	 */
	public List<Raca> findByEspecieOrderByNome(TipoEspecie cachorro);

	//public List<Raca> findByNomeContainingIgnoreCase(String nome);
	
}