package com.uhc.quatropatas.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uhc.quatropatas.controller.page.PageWrapper;
import com.uhc.quatropatas.controller.validator.AgendamentoValidator;
import com.uhc.quatropatas.model.Agendamento;
import com.uhc.quatropatas.model.AgendamentoServico;
import com.uhc.quatropatas.model.Animal;
import com.uhc.quatropatas.model.Servico;
import com.uhc.quatropatas.model.StatusAgendamento;
import com.uhc.quatropatas.repository.Agendamentos;
import com.uhc.quatropatas.repository.Animals;
import com.uhc.quatropatas.repository.Servicos;
import com.uhc.quatropatas.repository.filter.AgendamentoFilter;
import com.uhc.quatropatas.security.UsuarioSistema;
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
	
	@Autowired
	private Agendamentos agendamentos;
	
	/*
	 * Inicializando o binder apenas para o atributo agendamento
	 */
	@InitBinder("agendamento")
	public void inicializarValidador(WebDataBinder binder){
		binder.setValidator(agendamentoValidator);
	}
	
	@GetMapping("/novo")
	public ModelAndView novo(Agendamento agendamento){
		ModelAndView mv = new ModelAndView("agendamento/cadastro-agendamento");
		mv.addObject(agendamento);
		
		setUuid(agendamento);
		
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

	@PostMapping(value = "/novo", params = "salvar")
	public ModelAndView salvar(Agendamento agendamento, BindingResult result, RedirectAttributes attributes, 
			@AuthenticationPrincipal UsuarioSistema usuarioSistema){
		
		validarAgendamento(agendamento, result, usuarioSistema);
		if(result.hasErrors()){
			return novo(agendamento);
		}
		
		agendamentoService.salvar(agendamento);
		attributes.addFlashAttribute("mensagem", "Agendamento salvo com sucesso!");
		return new ModelAndView("redirect:/agendamentos/novo");
	}

	@PostMapping(value = "/novo", params = "agendar")
	public ModelAndView agendar(Agendamento agendamento, BindingResult result, RedirectAttributes attributes, 
			@AuthenticationPrincipal UsuarioSistema usuarioSistema){
		
		validarAgendamento(agendamento, result, usuarioSistema);
		if(result.hasErrors()){
			return novo(agendamento);
		}
		
		agendamentoService.agendar(agendamento);
		attributes.addFlashAttribute("mensagem", "Agendado com sucesso!");
		return new ModelAndView("redirect:/agendamentos/novo");
	}
	
	@PostMapping(value = "/novo", params = "enviarEmail")
	public ModelAndView enviarEmail(Agendamento agendamento, BindingResult result, RedirectAttributes attributes, 
			@AuthenticationPrincipal UsuarioSistema usuarioSistema){
		
		validarAgendamento(agendamento, result, usuarioSistema);
		if(result.hasErrors()){
			return novo(agendamento);
		}
		
		agendamentoService.salvar(agendamento);
		attributes.addFlashAttribute("mensagem", "Agendamento com sucesso e email enviado!");
		return new ModelAndView("redirect:/agendamentos/novo");
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo){
		
		//Agendamento agendamento = agendamentos.buscarComServicos(codigo);
		
		Agendamento agendamento = agendamentos.findOne(codigo);
		
		
		/*
		 * Setando o UUID antes, porque senão ele não teria UUID para passar.
		 */
		setUuid(agendamento);
		
		for(AgendamentoServico servico : agendamento.getAgendamentos()){
			tabelaServicosAgendamento.adicionarServico(agendamento.getUuid(), servico.getServico(), servico.getAnimal());
		}
		
		return novo(agendamento);
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

	@GetMapping
	public ModelAndView pesquisar(AgendamentoFilter agendamentoFilter, 
				@PageableDefault(size = 3) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/agendamento/pesquisa-agendamento");
		mv.addObject("todosStatus", StatusAgendamento.values());
		
		PageWrapper<Agendamento> paginaWrapper = new PageWrapper<>(agendamentos.filtrar(agendamentoFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	private ModelAndView mvTabelaServicosAgendamento(String uuid) {
		ModelAndView mv = new ModelAndView("agendamento/tabela-servicos-agendamento");
		mv.addObject("agendamentos", tabelaServicosAgendamento.getAgendamentos(uuid));
		mv.addObject("valorTotal", tabelaServicosAgendamento.getValorTotal(uuid));
		return mv;
	}
	
	private void validarAgendamento(Agendamento agendamento, BindingResult result, UsuarioSistema usuarioSistema) {
		agendamento.adicionarServicos(tabelaServicosAgendamento.getAgendamentos(agendamento.getUuid()));
		agendamento.calcularValorTotal();
		
		agendamento.setUsuario(usuarioSistema.getUsuario());
		/*
		 * Validando aqui, pq no corpo do metodo ele validaria antes de adicionar os
		 * serviços ao agendamento. Agora eu consigo primeiro adicionar os serviços
		 * e depois validar o agendamento todo.
		 */
		agendamentoValidator.validate(agendamento, result);
	}
	
	private void setUuid(Agendamento agendamento) {
		if(StringUtils.isEmpty(agendamento.getUuid())){
			agendamento.setUuid(UUID.randomUUID().toString());
		}
	}
}
