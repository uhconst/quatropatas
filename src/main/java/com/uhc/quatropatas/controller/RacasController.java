package com.uhc.quatropatas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uhc.quatropatas.controller.page.PageWrapper;
import com.uhc.quatropatas.model.Raca;
import com.uhc.quatropatas.model.TipoEspecie;
import com.uhc.quatropatas.repository.Racas;
import com.uhc.quatropatas.repository.filter.RacaFilter;
import com.uhc.quatropatas.service.RacaService;
import com.uhc.quatropatas.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/racas") //Definindo o "/racas" antes de todo mapping
public class RacasController {
	
	@Autowired
	private Racas racas;
	
	@Autowired
	private RacaService racaService;
	
	@GetMapping("/novo")
	public ModelAndView novo(Raca raca){
		ModelAndView mv = new ModelAndView("raca/cadastro-raca");
		mv.addObject(raca);
		mv.addObject("especies", TipoEspecie.values());
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Raca raca, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(raca);
		}
		
		racaService.salvar(raca);
		attributes.addFlashAttribute("mensagem", "Raça salva com sucesso!");
		return new ModelAndView("redirect:/racas/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(RacaFilter racaFilter, 
			@PageableDefault(size = 2) Pageable pageable, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("raca/pesquisa-raca");
		mv.addObject("especies", TipoEspecie.values());
		
		PageWrapper<Raca> paginaWrapper = new PageWrapper<>(racas.filtrar(racaFilter, pageable), 
				httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo){
		/*
		 *  Procura uma raça com o código que recebeu de 
		 *  parametro da URL mapeada
		 */
		Raca raca = racas.findOne(codigo);
		
		/*
		 * Já retorna uma raça preenchido
		 */
		return novo(raca);
	}
	
	@DeleteMapping("/{codigo}")
	public String deletar(@PathVariable Long codigo, RedirectAttributes attributes){
		try{
			racaService.deletar(codigo);
		}
		catch(ImpossivelExcluirEntidadeException e){
			attributes.addFlashAttribute("mensagemFalhaExclusao", e.getMessage());
			return "redirect:/racas";
		}
		attributes.addFlashAttribute("mensagem", "Raça deletada com sucesso!");
		return "redirect:/racas";
	}
}
