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

import com.uhc.quatropatas.model.Animal;
import com.uhc.quatropatas.model.TipoSexoAnimal;
import com.uhc.quatropatas.repository.Animals;
import com.uhc.quatropatas.repository.Racas;
import com.uhc.quatropatas.repository.filter.AnimalFilter;
import com.uhc.quatropatas.service.AnimalService;

@Controller
@RequestMapping("/animals") //Definindo o "/animals" antes de todo mapping
public class AnimalsController {
	
	@Autowired
	private Animals animals;
	
	@Autowired
	private Racas racas;
	
	@Autowired
	private AnimalService animalService;
	
	@GetMapping("/novo")
	public ModelAndView novo(Animal animal){
		ModelAndView mv = new ModelAndView("animal/cadastro-animal");
		mv.addObject(animal);
		mv.addObject("sexos", TipoSexoAnimal.values());
		mv.addObject("racas", racas.findAll());
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Animal animal, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(animal);
		}
		
		System.out.println(">>>>> Raca: " + animal.getRaca().getCodigo()); // APAGAR...
		
		animalService.salvar(animal);
		attributes.addFlashAttribute("mensagem", "Animal salvo com sucesso!");
		return new ModelAndView("redirect:/animals/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(AnimalFilter animalFilter){
		ModelAndView mv = new ModelAndView("animal/pesquisa-animal");
		mv.addObject("animals", animals.findByNomeContainingIgnoreCase(
				Optional.ofNullable(animalFilter.getNome()).orElse("%")));
		return mv;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo){
		/*
		 *  Procura um animal com o código que recebeu de 
		 *  parametro da URL mapeada
		 */
		Animal animal = animals.findOne(codigo);
		
		/*
		 * Já retorna um animal preenchido
		 */
		return novo(animal);
	}
	
	@DeleteMapping("/{codigo}")
	public String deletar(@PathVariable Long codigo, RedirectAttributes attributes){
		animalService.deletar(codigo);
		attributes.addFlashAttribute("mensagem", "Animal deletado com sucesso!");
		return "redirect:/animals";
	}
}
