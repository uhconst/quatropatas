package com.uhc.quatropatas.repository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.util.StringUtils;

import com.uhc.quatropatas.model.Grupo;
import com.uhc.quatropatas.model.Usuario;
import com.uhc.quatropatas.model.UsuarioGrupo;
import com.uhc.quatropatas.repository.filter.UsuarioFilter;

public class UsuariosImpl implements UsuariosQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Optional<Usuario> porUsernameAtivo(String username) {
		
		/*
		 * Usando JPQL para a consulta
		 */
		return manager
				.createQuery("from Usuario where lower(username) = lower(:username) and ativo = true", Usuario.class)
				.setParameter("username", username).getResultList().stream().findFirst();
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		return manager
				.createQuery("select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u  = :usuario"
						, String.class)
				.setParameter("usuario", usuario)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> filtrar(UsuarioFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		
		/*
		 * Mandando fazer um distinct na entidade principal, a Usuario
		 * Sem isso ele retornava a pessoa duas vezes caso estivesse nos dois grupos.
		 */
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		adicionarFiltro(filtro, criteria);
		
		return criteria.list();
	}

	private void adicionarFiltro(UsuarioFilter filtro, Criteria criteria) {
		if(filtro != null){
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(
						Restrictions.or(
								Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE),
								Restrictions.ilike("sobrenome", filtro.getNome(), MatchMode.ANYWHERE)));
			}
			
			criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
			
			if(filtro.getGrupos()!=null && !filtro.getGrupos().isEmpty()){
				
				List<Criterion> subqueries = new ArrayList<>();
				/*
				 * Transformando em String e fazendo o mapeamento para Long.
				 * Usando o toArray no fim para o for funcionar.
				 */
				for(Long codigoGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()){
					DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
					dc.add(Restrictions.eq("id.grupo.codigo", codigoGrupo));
					dc.setProjection(Projections.property("id.usuario"));
					
					subqueries.add(Subqueries.propertyIn("codigo", dc));
				}
				
				/*
				 * Transformando a lista Subqueries em um Array
				 */
				Criterion[] criterions = new Criterion[subqueries.size()];
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
			}
		}
		
	}

}
