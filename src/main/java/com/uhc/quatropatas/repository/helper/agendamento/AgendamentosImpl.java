package com.uhc.quatropatas.repository.helper.agendamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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

import com.uhc.quatropatas.dto.AgendamentoMes;
import com.uhc.quatropatas.model.Agendamento;
import com.uhc.quatropatas.model.StatusAgendamento;
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

	@Override
	public BigDecimal valorTotalNoAno() {
		/*
		 * Fazendo a consulta com JPQL
		 */
		Optional<BigDecimal> optional =  Optional.ofNullable(
			manager.createQuery("select sum(valorTotal) from Agendamento where year(dataHoraAgendamento) = :ano and status = :status", BigDecimal.class)
				.setParameter("ano", Year.now().getValue())
				.setParameter("status", StatusAgendamento.AGENDADO)
				.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	@Override
	public BigDecimal valorTotalNoMes() {
		/*
		 * Fazendo a consulta com JPQL
		 */
		Optional<BigDecimal> optional =  Optional.ofNullable(
			manager.createQuery("select sum(valorTotal) from Agendamento where month(dataHoraAgendamento) = :mes and status = :status", BigDecimal.class)
				.setParameter("mes", MonthDay.now().getMonthValue())
				.setParameter("status", StatusAgendamento.AGENDADO)
				.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	@Override
	public BigDecimal valorTicketMedioNoAno() {
        /*
         * Fazendo a consulta com JPQL
         */
        Optional<BigDecimal> optional =  Optional.ofNullable(
            manager.createQuery("select sum(valorTotal)/count(*) from Agendamento where year(dataHoraAgendamento) = :ano and status = :status", BigDecimal.class)
                .setParameter("ano", Year.now().getValue())
                .setParameter("status", StatusAgendamento.AGENDADO)
                .getSingleResult());
        return optional.orElse(BigDecimal.ZERO);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AgendamentoMes> totalPorMes() {
		/*
		 * Pegando a data do sexto mes atras
		 */
		String ultimosSeisMeses = LocalDate.now().minusMonths(6).format(DateTimeFormatter.ofPattern("yyyy/MM"));
		
		String query = "select new com.uhc.quatropatas.dto.AgendamentoMes(to_char(data_hora_agendamento, 'YYYY/MM'), count(*)) from Agendamento where to_char(data_hora_agendamento, 'YYYY/MM') > :sextoMes and status = 'AGENDADO' group by to_char(data_hora_agendamento, 'YYYY/MM') order by to_char(data_hora_agendamento, 'YYYY/MM') desc";
		
		
		List<AgendamentoMes> agendamentosMes = manager.createQuery(query)
				.setParameter("sextoMes", ultimosSeisMeses)
				.getResultList();
		
		/*
		List<AgendamentoMes> agendamentosMes = manager.createNamedQuery("Agendamentos.totalPorMes").getResultList();
		*/
		
		/*
		 * Percorrendo mes a mes dos ultimos 6 meses
		 * e verificando se tem o mes mesIdeal na lista.
		 * Caso não tenha adiciona o mesIdeal nela (o mes faltando)
		 * e seta como 0 sua quantidade de agendamento.
		 * Porque quando busca do banco o banco não retornava nada
		 * para quando não tinha agendamento, ai quebrava o grafico
		 * por pular algum mes.
		 */
		LocalDate hoje = LocalDate.now();
		for (int i = 1; i <= 6; i++) {
			String mesIdeal = String.format("%d/%02d", hoje.getYear(), hoje.getMonthValue());

			boolean possuiMes = agendamentosMes.stream().filter(v -> v.getMes().equals(mesIdeal)).findAny().isPresent();
			
			if (!possuiMes) {
				agendamentosMes.add(i - 1, new AgendamentoMes(mesIdeal, (long) 0));
			}
			
			hoje = hoje.minusMonths(1);
		}
		return agendamentosMes;
	}
	
//	@Override
//	public Agendamento buscarComServicos(Long codigo){
//		Criteria criteria = manager.unwrap(Session.class).createCriteria(Agendamento.class);
//		criteria.createAlias("agendamentos", "a", JoinType.LEFT_OUTER_JOIN);
//		criteria.add(Restrictions.eq("codigo", codigo));
//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//		return (Agendamento) criteria.uniqueResult();
//	}
	
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
				//criteria.add(Restrictions.ilike("p.nome", filtro.getNomePessoa(), MatchMode.ANYWHERE));
				
				criteria.add(
						Restrictions.or(
								Restrictions.ilike("p.nome", filtro.getNomePessoa(), MatchMode.ANYWHERE),
								Restrictions.ilike("p.sobrenome", filtro.getNomePessoa(), MatchMode.ANYWHERE)));
			}
			
			if (!StringUtils.isEmpty(filtro.getCpfPessoa())) {
				criteria.add(Restrictions.ilike("p.cpf", filtro.getCpfPessoa(), MatchMode.ANYWHERE));
			}
		}
	}

}
