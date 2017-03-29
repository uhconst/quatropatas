package com.uhc.quatropatas.repository.helper.servico;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import com.uhc.quatropatas.model.Servico;
import com.uhc.quatropatas.repository.filter.ServicoFilter;

public class ServicosImpl implements ServicosQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Servico> filtrar(ServicoFilter filtro) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Servico.class);
		if(filtro != null){
			if(!StringUtils.isEmpty(filtro.getDescricao())){
				criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
			}
			
			if (filtro.getDuracaoDe() != null) {
				criteria.add(Restrictions.ge("duracao", filtro.getDuracaoDe()));
			}

			if (filtro.getDuracaoAte() != null) {
				criteria.add(Restrictions.le("duracao", filtro.getDuracaoAte()));
			}
			
			if (filtro.getValorDe() != null) {
				criteria.add(Restrictions.ge("valor", filtro.getValorDe()));
			}

			if (filtro.getValorAte() != null) {
				criteria.add(Restrictions.le("valor", filtro.getValorAte()));
			}
		}

		return criteria.list();
	}

}
