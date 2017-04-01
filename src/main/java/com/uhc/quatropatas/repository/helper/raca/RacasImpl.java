package com.uhc.quatropatas.repository.helper.raca;

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

import com.uhc.quatropatas.model.Raca;
import com.uhc.quatropatas.repository.filter.RacaFilter;
import com.uhc.quatropatas.repository.paginacao.PaginacaoUtil;

public class RacasImpl implements RacasQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	//@Transactional(readOnly = true)
	public Page<Raca> filtrar(RacaFilter filtro, Pageable pageable) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Raca.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(filtro, criteria);
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private Long total(RacaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Raca.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(RacaFilter filtro, Criteria criteria) {
		if(filtro != null){
			if(!StringUtils.isEmpty(filtro.getNome())){
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			if(filtro.getEspecie() != null){
				criteria.add(Restrictions.eq("especie", filtro.getEspecie()));
			}
		}
	}

}
