package com.uhc.quatropatas.session;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.uhc.quatropatas.model.Animal;
import com.uhc.quatropatas.model.Servico;

public class TabelaServicosAgendamentoTest {
	
	private TabelaServicosAgendamento tabelaServicosAgendamento;
	
	@Before
	public void setUp(){
		this.tabelaServicosAgendamento = new TabelaServicosAgendamento("1");
	}
	
	/*
	 * Verificando se a lista for vazia retorna ZERO
	 */
	@Test
	public void deveCalcularValorTotalSemServicos() throws Exception {
		assertEquals(BigDecimal.ZERO, tabelaServicosAgendamento.getValorTotal());
	}
	
	@Test
	public void deveCalcularValorTotalComUmServico() throws Exception {
		Servico servico = new Servico();
		Animal animal = new Animal();
		BigDecimal valor = new BigDecimal("8.90");
		servico.setValor(valor);
		
		tabelaServicosAgendamento.adicionarServico(servico, animal);
		
		assertEquals(valor, tabelaServicosAgendamento.getValorTotal());
	}
	
	@Test
	public void deveCalcularValorTotalComVariosServicos() throws Exception {
		Animal animal = new Animal();
		
		Servico s1 = new Servico();
		BigDecimal v1 = new BigDecimal("10.00");
		s1.setValor(v1);
		
		Servico s2 = new Servico();
		BigDecimal v2 = new BigDecimal("5.00");
		s2.setValor(v2);
		
		tabelaServicosAgendamento.adicionarServico(s1, animal);
		tabelaServicosAgendamento.adicionarServico(s2, animal);
		
		assertEquals(v1.add(v2), tabelaServicosAgendamento.getValorTotal());
	}
	
	@Test
	public void deveExcluirAgendamento() throws Exception {
		Animal a1 = new Animal();
		a1.setNome("Test1");
		
		Animal a2 = new Animal();
		a2.setNome("Test2");
		
		Animal a3 = new Animal();
		a3.setNome("Test3");
		
		Servico s1 = new Servico();
		BigDecimal v1 = new BigDecimal("10.00");
		s1.setValor(v1);
		
		Servico s2 = new Servico();
		BigDecimal v2 = new BigDecimal("5.00");
		s2.setValor(v2);
		
		Servico s3 = new Servico();
		BigDecimal v3 = new BigDecimal("2.00");
		s3.setValor(v3);
		
		tabelaServicosAgendamento.adicionarServico(s2, a1);
		tabelaServicosAgendamento.adicionarServico(s2, a2);
		tabelaServicosAgendamento.adicionarServico(s3, a2);
		
		
		tabelaServicosAgendamento.deletarServico(s2, a2);
		
		assertEquals(2, tabelaServicosAgendamento.total());
		assertEquals(v2.add(v2), tabelaServicosAgendamento.getValorTotal());
	}
}
