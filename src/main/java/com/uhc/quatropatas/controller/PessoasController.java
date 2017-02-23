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

import com.uhc.quatropatas.model.Pessoa;
import com.uhc.quatropatas.model.TipoSexo;
import com.uhc.quatropatas.repository.Pessoas;
import com.uhc.quatropatas.repository.filter.PessoaFilter;

@Controller
@RequestMapping("/pessoas") //Definindo o "/pessoas" antes de todo mapping
public class PessoasController {
	
	@Autowired
	private Pessoas pessoas;
	
	@GetMapping("/novo")
	public ModelAndView novo(Pessoa pessoa){
		ModelAndView mv = new ModelAndView("pessoa/cadastro-pessoa");
		mv.addObject(pessoa);
		mv.addObject("sexos", TipoSexo.values());
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(pessoa);
		}
		
		pessoas.save(pessoa);
		attributes.addFlashAttribute("mensagem", "Pessoa salva com sucesso!");
		return new ModelAndView("redirect:/pessoas/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(PessoaFilter pessoaFilter){
		ModelAndView mv = new ModelAndView("pessoa/pesquisa-pessoa");
		mv.addObject("pessoas", pessoas.findByNomeContainingIgnoreCase(
				Optional.ofNullable(pessoaFilter.getNome()).orElse("%")));
		return mv;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo){
		/*
		 *  Procura uma pessoa com o código que recebeu de 
		 *  parametro da URL mapeada
		 */
		Pessoa pessoa = pessoas.findOne(codigo);
		
		/*
		 * Já retorna uma pessoa preenchido
		 */
		return novo(pessoa);
	}
	
	@DeleteMapping("/{codigo}")
	public String deletar(@PathVariable Long codigo, RedirectAttributes attributes){
		pessoas.delete(codigo);
		attributes.addFlashAttribute("mensagem", "Pessoa deletada com sucesso!");
		return "redirect:/pessoas";
	}
}