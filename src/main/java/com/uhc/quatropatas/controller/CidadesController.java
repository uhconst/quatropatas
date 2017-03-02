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

import com.uhc.quatropatas.model.Cidade;
import com.uhc.quatropatas.model.TipoEspecie;
import com.uhc.quatropatas.repository.Cidades;

@Controller
@RequestMapping("/cidades") //Definindo o "/cidades" antes de todo mapping
public class CidadesController {
	
	@Autowired
	private Cidades cidades;
	
	@GetMapping("/novo")
	public ModelAndView novo(Cidade cidade){
		ModelAndView mv = new ModelAndView("cidade/cadastro-cidade");
		mv.addObject(cidade);
		mv.addObject("especies", TipoEspecie.values());
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(cidade);
		}
		
		cidades.save(cidade);
		attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso!");
		return new ModelAndView("redirect:/cidades/novo");
	}
	/*
	@GetMapping
	public ModelAndView pesquisar(CidadeFilter cidadeFilter){
		ModelAndView mv = new ModelAndView("cidade/pesquisa-cidade");
		mv.addObject("cidades", cidades.findByNomeContainingIgnoreCase(
				Optional.ofNullable(cidadeFilter.getNome()).orElse("%")));
		return mv;
	}
	*/
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo){
		/*
		 *  Procura uma cidade com o código que recebeu de 
		 *  parametro da URL mapeada
		 */
		Cidade cidade = cidades.findOne(codigo);
		
		/*
		 * Já retorna uma cidade preenchido
		 */
		return novo(cidade);
	}
	
	@DeleteMapping("/{codigo}")
	public String deletar(@PathVariable Long codigo, RedirectAttributes attributes){
		cidades.delete(codigo);
		attributes.addFlashAttribute("mensagem", "Cidade deletado com sucesso!");
		return "redirect:/cidades";
	}
}