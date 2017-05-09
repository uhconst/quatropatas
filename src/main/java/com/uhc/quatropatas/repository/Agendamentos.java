package com.uhc.quatropatas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhc.quatropatas.model.Agendamento;
import com.uhc.quatropatas.model.StatusAgendamento;
import com.uhc.quatropatas.repository.helper.agendamento.AgendamentosQueries;

public interface Agendamentos extends JpaRepository<Agendamento, Long>, AgendamentosQueries {

	public Long countByStatus(StatusAgendamento cancelado);

}