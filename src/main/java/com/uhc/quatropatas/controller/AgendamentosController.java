package com.uhc.quatropatas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uhc.quatropatas.model.Animal;
import com.uhc.quatropatas.model.Servico;
import com.uhc.quatropatas.repository.Animals;
//import com.uhc.quatropatas.model.Agendamento;
import com.uhc.quatropatas.repository.Servicos;
import com.uhc.quatropatas.session.TabelaServicosAgendamento;
//import com.uhc.quatropatas.service.AgendamentoService;


@Controller
@RequestMapping("/agendamentos") //Definindo o "/agendamentos" antes de todo mapping
public class AgendamentosController {

	@Autowired
	private Servicos servicos;
	
	@Autowired
	private Animals animals;
	
	@Autowired
	private TabelaServicosAgendamento tabelaServicosAgendamento;
	
	//@Autowired
	//private AgendamentoService agendamentoService;
	
	@GetMapping("/novo")
	public ModelAndView novo(){//(Agendamento agendamento){
		ModelAndView mv = new ModelAndView("agendamento/cadastro-agendamento");
		//mv.addObject(agendamento);
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
	public @ResponseBody String adicionarServico(Long codigoServico, Long codigoAnimal){
		System.out.println(">>Dentro do controller: " + codigoServico);
		Animal animal = animals.findOne(codigoAnimal);
		Servico servico = servicos.findOne(codigoServico);
		//Animal animal = new Animal();
		//Servico servico = new Servico();
		tabelaServicosAgendamento.adicionarServico(servico, animal);
		System.out.println(">>Total de servicos: " + tabelaServicosAgendamento.total());
		return "Servico adicionado!";
	}
}
