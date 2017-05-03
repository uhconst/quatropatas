package com.uhc.quatropatas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uhc.quatropatas.dto.AgendamentoMes;
import com.uhc.quatropatas.repository.Agendamentos;
import com.uhc.quatropatas.repository.Animals;
import com.uhc.quatropatas.repository.Pessoas;

@Controller
public class DashboardController {
	
	@Autowired
	private Agendamentos agendamentos;
	
	@Autowired
	private Pessoas pessoas;
	
	@Autowired
	private Animals animals;
	
	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");
		
		mv.addObject("totalAnimais", animals.count());
		mv.addObject("totalClientes", pessoas.count());
		
		mv.addObject("agendamentosNoAno", agendamentos.valorTotalNoAno());
		mv.addObject("agendamentosNoMes", agendamentos.valorTotalNoMes());
		mv.addObject("ticketMedioNoAno", agendamentos.valorTicketMedioNoAno());
		
		return mv;
	}
	
	@GetMapping("/totalPorMes")
	public @ResponseBody List<AgendamentoMes> listarTotalVendaPorMes() {
		return agendamentos.totalPorMes();
	}
}
