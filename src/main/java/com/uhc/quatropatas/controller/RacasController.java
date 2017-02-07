package com.uhc.quatropatas.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uhc.quatropatas.model.Raca;
import com.uhc.quatropatas.model.TipoEspecie;
import com.uhc.quatropatas.repository.Racas;
import com.uhc.quatropatas.repository.filter.RacaFilter;

@Controller
@RequestMapping("/racas") //Definindo o "/racas" antes de todo mapping
public class RacasController {
	
	@Autowired
	private Racas racas;
	
	@GetMapping("/novo")
	public ModelAndView novo(Raca raca){
		ModelAndView mv = new ModelAndView("raca/cadastro-raca");
		mv.addObject("especies", TipoEspecie.values());
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Raca raca, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(raca);
		}
		
		racas.save(raca);
		attributes.addFlashAttribute("mensagem", "Raça salva com sucesso!");
		return new ModelAndView("redirect:/racas/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(RacaFilter racaFilter){
		ModelAndView mv = new ModelAndView("raca/pesquisa-raca");
		mv.addObject("racas", racas.findByNomeContainingIgnoreCase(
				Optional.ofNullable(racaFilter.getNome()).orElse("%")));
		return mv;
	}
}
