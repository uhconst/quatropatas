package com.uhc.quatropatas.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uhc.quatropatas.model.Servico;
//import com.uhc.quatropatas.repository.Servicos;
//import com.uhc.quatropatas.repository.filter.ServicoFilter;
//import com.uhc.quatropatas.service.ServicoService;
import com.uhc.quatropatas.repository.Servicos;
import com.uhc.quatropatas.service.ServicoService;

@Controller
@RequestMapping("/servicos") //Definindo o "/servicos" antes de todo mapping
public class ServicosController {
	
	@Autowired
	private Servicos servicos;
	
	@Autowired
	private ServicoService servicoService;
	
	@GetMapping("/novo")
	public ModelAndView novo(Servico servico){
		ModelAndView mv = new ModelAndView("servico/cadastro-servico");
		mv.addObject(servico);
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Servico servico, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(servico);
		}
		
		servicoService.salvar(servico);
		attributes.addFlashAttribute("mensagem", "Serviço salvo com sucesso!");
		return new ModelAndView("redirect:/servicos/novo");
	}
	
	/*
	@GetMapping
	public ModelAndView pesquisar(ServicoFilter servicoFilter){
		ModelAndView mv = new ModelAndView("servico/pesquisa-servico");
		mv.addObject("servicos", servicos.findByNomeContainingIgnoreCase(
				Optional.ofNullable(servicoFilter.getNome()).orElse("%")));
		return mv;
	}
	*/
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo){
	
		/*
		 *  Procura uma raça com o código que recebeu de 
		 *  parametro da URL mapeada
		 */
		Servico servico = servicos.findOne(codigo);
		
		/*
		 * Já retorna uma raça preenchido
		 */
	
		return novo(servico);
	}
	
	@DeleteMapping("/{codigo}")
	public String deletar(@PathVariable Long codigo, RedirectAttributes attributes){
		servicoService.deletar(codigo);
		attributes.addFlashAttribute("mensagem", "Serviço deletado com sucesso!");
		return "redirect:/servicos";
	}

}
