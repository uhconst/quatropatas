package com.uhc.quatropatas.repository.helper.agendamento;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.uhc.quatropatas.model.Agendamento;
import com.uhc.quatropatas.repository.filter.AgendamentoFilter;
import com.uhc.quatropatas.repository.paginacao.PaginacaoUtil;

public class AgendamentosImpl implements AgendamentosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<Agendamento> filtrar(AgendamentoFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Agendamento.class);
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(AgendamentoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Agendamento.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(AgendamentoFilter filtro, Criteria criteria) {
		criteria.createAlias("pessoa", "p");
		
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getCodigo())) {
				criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
			}
			
			if (filtro.getStatus() != null) {
				criteria.add(Restrictions.eq("status", filtro.getStatus()));
			}
			
			if (filtro.getDesdeAgendamento() != null) {
				LocalDateTime desdeAgendamento = LocalDateTime.of(filtro.getDesdeAgendamento(), LocalTime.of(0, 0));
				criteria.add(Restrictions.ge("dataAgendamento", desdeAgendamento));
			}
			
			if (filtro.getAteAgendamento() != null) {
				LocalDateTime ateAgendamento = LocalDateTime.of(filtro.getAteAgendamento(), LocalTime.of(23, 59));
				criteria.add(Restrictions.le("dataAgendamento", ateAgendamento));
			}
			
			if (filtro.getDesdeCriacao() != null) {
				LocalDateTime desdeCriacao = LocalDateTime.of(filtro.getDesdeCriacao(), LocalTime.of(0, 0));
				criteria.add(Restrictions.ge("dataCriacao", desdeCriacao));
			}
			
			if (filtro.getAteCriacao() != null) {
				LocalDateTime ateCriacao = LocalDateTime.of(filtro.getAteCriacao(), LocalTime.of(23, 59));
				criteria.add(Restrictions.le("dataCriacao", ateCriacao));
			}
			
			if (filtro.getValorMinimo() != null) {
				criteria.add(Restrictions.ge("valorTotal", filtro.getValorMinimo()));
			}
			
			if (filtro.getValorMaximo() != null) {
				criteria.add(Restrictions.le("valorTotal", filtro.getValorMaximo()));
			}
			
			if (!StringUtils.isEmpty(filtro.getNomePessoa())) {
				criteria.add(Restrictions.ilike("p.nome", filtro.getNomePessoa(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filtro.getCpfPessoa())) {
				criteria.add(Restrictions.ilike("p.cpf", filtro.getCpfPessoa(), MatchMode.ANYWHERE));
			}
		}
	}

}
