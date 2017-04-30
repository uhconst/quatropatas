package com.uhc.quatropatas.repository.helper.animal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import com.uhc.quatropatas.model.Animal;
import com.uhc.quatropatas.repository.filter.AnimalFilter;

public class AnimalsImpl implements AnimalsQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Animal> filtrar(AnimalFilter filtro) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Animal.class);
		
		adicionarFiltro(filtro, criteria);
		
		return criteria.list();
	}

	private void adicionarFiltro(AnimalFilter filtro, Criteria criteria) {
		if(filtro != null){
			if(!StringUtils.isEmpty(filtro.getNome())){
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			if (filtro.getPesoDe() != null) {
				criteria.add(Restrictions.ge("peso", filtro.getPesoDe()));
			}

			if (filtro.getPesoAte() != null) {
				criteria.add(Restrictions.le("peso", filtro.getPesoAte()));
			}
			
			if (filtro.getSexo() != null) {
				System.out.println(">>>>>>>Dentro do Impl, sexo: " + filtro.getSexo());
				criteria.add(Restrictions.eq("sexo", filtro.getSexo()));
			}
			
			if(isRacaInformada(filtro)){
				criteria.add(Restrictions.eq("raca", filtro.getRaca()));
			}
		}
	}
	
	private boolean isRacaInformada(AnimalFilter filtro) {
		return filtro.getRaca() != null && filtro.getRaca().getCodigo() != null;
	}

}
