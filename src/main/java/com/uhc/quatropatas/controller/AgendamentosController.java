package com.uhc.quatropatas.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uhc.quatropatas.controller.validator.AgendamentoValidator;
import com.uhc.quatropatas.model.Agendamento;
import com.uhc.quatropatas.model.Animal;
import com.uhc.quatropatas.model.Servico;
import com.uhc.quatropatas.repository.Animals;
import com.uhc.quatropatas.repository.Servicos;
import com.uhc.quatropatas.service.AgendamentoService;
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
	
	@Autowired
	private AgendamentoService agendamentoService;
	
	@Autowired
	private AgendamentoValidator agendamentoValidator;
	
	@InitBinder
	public void inicializarValidador(WebDataBinder binder){
		binder.setValidator(agendamentoValidator);
	}
	
	@GetMapping("/novo")
	public ModelAndView novo(Agendamento agendamento){
		ModelAndView mv = new ModelAndView("agendamento/cadastro-agendamento");
		//mv.addObject(agendamento);
		if(StringUtils.isEmpty(agendamento.getUuid())){
			agendamento.setUuid(UUID.randomUUID().toString());
		}
		
		/*
		 * Mantendo os valores da lista de agendamento.
		 * Caso falhe a validação, vai manter os servicos que estao sendo
		 * agendados na tela
		 */
		mv.addObject("agendamentos", agendamento.getAgendamentos());
		mv.addObject("valorDesconto", agendamento.getValorDesconto());
		mv.addObject("valorTotalServicos", tabelaServicosAgendamento.getValorTotal(agendamento.getUuid()));
		
		mv.addObject("servicos", servicos.findAll());
		
		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(Agendamento agendamento, BindingResult result, RedirectAttributes attributes){
		agendamento.adicionarServicos(tabelaServicosAgendamento.getAgendamentos(agendamento.getUuid()));
		agendamento.calcularValorTotal();
		
		/*
		 * Validando aqui, pq no corpo do metodo ele validaria antes de adicionar os
		 * serviços ao agendamento. Agora eu consigo primeiro adicionar os serviços
		 * e depois validar o agendamento todo.
		 */
		agendamentoValidator.validate(agendamento, result);
		if(result.hasErrors()){
			return novo(agendamento);
		}
		
		/*
		@AuthenticationPrincipal Usuario Sistema usuarioSistema
		aula 23.15 aos 10:50
		*/
		
		agendamentoService.salvar(agendamento);
		attributes.addFlashAttribute("mensagem", "Agendamento salvo com sucesso!");
		return new ModelAndView("redirect:/agendamentos/novo");
	}

	
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
