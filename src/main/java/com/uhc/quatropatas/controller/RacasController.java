package com.uhc.quatropatas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.uhc.quatropatas.model.Raca;
import com.uhc.quatropatas.model.TipoEspecie;

@Controller
public class RacasController {
	
	@GetMapping("/racas/novo")
	public String novo(Model model){
		model.addAttribute(new Raca());
		model.addAttribute("especies", TipoEspecie.values());
		
		return "raca/cadastro-raca";
	}
	
	@PostMapping("/racas/novo")
	public String salvar(Raca raca){
		return "";
	}
}
