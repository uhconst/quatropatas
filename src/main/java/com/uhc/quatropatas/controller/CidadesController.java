package com.uhc.quatropatas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uhc.quatropatas.model.Cidade;
import com.uhc.quatropatas.repository.Cidades;
import com.uhc.quatropatas.repository.Estados;
import com.uhc.quatropatas.repository.filter.CidadeFilter;
import com.uhc.quatropatas.service.CidadeService;

@Controller
@RequestMapping("/cidades") //Definindo o "/cidades" antes de todo mapping
public class CidadesController {
	
	@Autowired
	private Cidades cidades;
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping("/novo")
	public ModelAndView novo(Cidade cidade){
		ModelAndView mv = new ModelAndView("cidade/cadastro-cidade");
		mv.addObject(cidade);
		mv.addObject("estados", estados.findAll());
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(cidade);
		}
		
		cidadeService.salvar(cidade);
		attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso!");
		return new ModelAndView("redirect:/cidades/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(CidadeFilter cidadeFilter){
		ModelAndView mv = new ModelAndView("cidade/pesquisa-cidade");
		mv.addObject("estados", estados.findAll());
		
		mv.addObject("cidades", cidades.filtrar(cidadeFilter));
		return mv;
	}
	
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
		cidadeService.deletar(codigo);
		attributes.addFlashAttribute("mensagem", "Cidade deletado com sucesso!");
		return "redirect:/cidades";
	}
	
	/*
	 * Se fizer um GET em cidades dizendo que precisa 
	 * receber um request, cai nesse metodo.
	 * Usa Default como -1 para caso nao passar nada retornar um array vazio.
	 * Isso aconteceria caso selecionar um estado que não tem cidades, não 
	 * encontraria nada.
	 */
	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisaPorCodigoEstado(
			@RequestParam(name="estado", defaultValue = "-1") Long codigoEstado){
		return cidades.findByEstadoCodigo(codigoEstado);
	}
	
}