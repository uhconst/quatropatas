package com.uhc.quatropatas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Servico;
import com.uhc.quatropatas.repository.helper.servico.ServicosQueries;

public interface Servicos extends JpaRepository<Servico, Long>, ServicosQueries{
	
}
