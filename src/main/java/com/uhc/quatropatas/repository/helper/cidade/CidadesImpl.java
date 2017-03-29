package com.uhc.quatropatas.repository.helper.cidade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import com.uhc.quatropatas.model.Cidade;
import com.uhc.quatropatas.repository.filter.CidadeFilter;

public class CidadesImpl implements CidadesQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cidade> filtrar(CidadeFilter filtro) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		if(filtro != null){
			if(!StringUtils.isEmpty(filtro.getNome())){
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			if(isEstadoInformado(filtro)){
				criteria.add(Restrictions.eq("estado", filtro.getEstado()));
			}
		}
		
		return criteria.list();
	}

	private boolean isEstadoInformado(CidadeFilter filtro) {
		return filtro.getEstado() != null && filtro.getEstado().getCodigo() != null;
	}

}
