package com.uhc.quatropatas.repository.helper.raca;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import com.uhc.quatropatas.model.Raca;
import com.uhc.quatropatas.repository.filter.RacaFilter;

public class RacasImpl implements RacasQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	//@Transactional(readOnly = true)
	public List<Raca> filtrar(RacaFilter filtro) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Raca.class);
		if(filtro != null){
			if(!StringUtils.isEmpty(filtro.getNome())){
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			if(filtro.getEspecie() != null){
				criteria.add(Restrictions.eq("especie", filtro.getEspecie()));
			}
		}

		return criteria.list();
	}
}
