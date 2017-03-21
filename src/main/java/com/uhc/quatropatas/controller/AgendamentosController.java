package com.uhc.quatropatas.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uhc.quatropatas.model.Animal;
import com.uhc.quatropatas.model.Servico;
import com.uhc.quatropatas.repository.Animals;
import com.uhc.quatropatas.repository.Servicos;
import com.uhc.quatropatas.session.TabelasAgendamentosSession;


@Controller
@RequestMapping("/agendamentos") //Definindo o "/agendamentos" antes de todo mapping
public class AgendamentosController {

	@Autowired
	private Servicos servicos;
	
	@Autowired
	private Animals animals;
	
	@Autowired
	private TabelasAgendamentosSession tabelaServicosAgendamento;
	
	//@Autowired
	//private AgendamentoService agendamentoService;
	
	@GetMapping("/novo")
	public ModelAndView novo(){//(Agendamento agendamento){
		ModelAndView mv = new ModelAndView("agendamento/cadastro-agendamento");
		//mv.addObject(agendamento);
		mv.addObject("uuid", UUID.randomUUID().toString());
		mv.addObject("servicos", servicos.findAll());
		
		return mv;
	}
	/*
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Agendamento agendamento, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(agendamento);
		}
		
		agendamentoService.salvar(agendamento);
		attributes.addFlashAttribute("mensagem", "Agendamento salvo com sucesso!");
		return new ModelAndView("redirect:/agendamentos/novo");
	}
	*/
	
	@PostMapping("/agendamentoservico")
	public @ResponseBody ModelAndView adicionarServico(Long codigoServico, Long codigoAnimal, String uuid){
		Animal animal = animals.findOne(codigoAnimal);
		Servico servico = servicos.findOne(codigoServico);
		tabelaServicosAgendamento.adicionarServico(uuid, servico, animal);
		return mvTabelaServicosAgendamento(uuid);
	}
	
	@DeleteMapping("agendamentoservico/{uuid}/{codigoServico}/{codigoAnimal}")
	public ModelAndView deletarServico(@PathVariable Long codigoServico, 
				@PathVariable Long codigoAnimal, @PathVariable String uuid){
		Animal animal = animals.findOne(codigoAnimal);
		Servico servico = servicos.findOne(codigoServico);
		tabelaServicosAgendamento.deletarServico(uuid, servico, animal);
		return mvTabelaServicosAgendamento(uuid);
	}

	private ModelAndView mvTabelaServicosAgendamento(String uuid) {
		ModelAndView mv = new ModelAndView("agendamento/tabela-servicos-agendamento");
		mv.addObject("agendamentos", tabelaServicosAgendamento.getAgendamentos(uuid));
		mv.addObject("valorTotal", tabelaServicosAgendamento.getValorTotal(uuid));
		return mv;
	}
}
