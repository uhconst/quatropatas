package com.uhc.quatropatas.repository.paginacao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/*
 * Componente para injetar em todo Impl que usará paginação
 */
@Component
public class PaginacaoUtil {
	
	public void preparar(Criteria criteria, Pageable pageable){
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		/*
		 * Calculando o primeiro registro da pagina
		 */
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;

		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistrosPorPagina);
		
		Sort sort = pageable.getSort();
		if(sort != null){
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			criteria.addOrder(order.isAscending() ? Order.asc(property) : Order.desc(property));
		}
	} 
}
