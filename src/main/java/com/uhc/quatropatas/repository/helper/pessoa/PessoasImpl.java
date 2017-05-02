package com.uhc.quatropatas.repository.helper.pessoa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import com.uhc.quatropatas.model.Pessoa;
import com.uhc.quatropatas.repository.filter.PessoaFilter;

public class PessoasImpl implements PessoasQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> filtrar(PessoaFilter filtro) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Pessoa.class);
		
		adicionarFiltro(filtro, criteria);
		
		return criteria.list();
	}
	
	private void adicionarFiltro(PessoaFilter filtro, Criteria criteria) {
		if(filtro != null){
			if(!StringUtils.isEmpty(filtro.getNome())){
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			if(!StringUtils.isEmpty(filtro.getSobrenome())){
				criteria.add(Restrictions.ilike("sobrenome", filtro.getSobrenome(), MatchMode.ANYWHERE));
			}
			
			if(!StringUtils.isEmpty(filtro.getCpf())){
				criteria.add(Restrictions.ilike("cpf", filtro.getCpf(), MatchMode.ANYWHERE));
			}
		}
	}

}
