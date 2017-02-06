package com.uhc.quatropatas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uhc.quatropatas.model.Raca;
import com.uhc.quatropatas.model.TipoEspecie;
import com.uhc.quatropatas.repository.Racas;

@Controller
public class RacasController {
	
	@Autowired
	private Racas racas;
	
	@GetMapping("/racas/novo")
	public ModelAndView novo(Raca raca){
		ModelAndView mv = new ModelAndView("raca/cadastro-raca");
		mv.addObject("especies", TipoEspecie.values());
		
		return mv;
	}
	
	@PostMapping("/racas/novo")
	public ModelAndView salvar(@Valid Raca raca, BindingResult result){
		if(result.hasErrors()){
			return novo(raca);
		}
		
		racas.save(raca);
		return new ModelAndView("redirect:/racas/novo");
	}
}
