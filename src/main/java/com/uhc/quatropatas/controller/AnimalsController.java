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

import com.uhc.quatropatas.model.Animal;
import com.uhc.quatropatas.model.TipoEspecie;
import com.uhc.quatropatas.model.TipoSexoAnimal;
import com.uhc.quatropatas.repository.Animals;
import com.uhc.quatropatas.repository.Racas;
import com.uhc.quatropatas.repository.filter.AnimalFilter;
import com.uhc.quatropatas.service.AnimalService;
import com.uhc.quatropatas.service.exception.ImpossivelExcluirEntidadeException;

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
		//mv.addObject("racas", racas.findAll());
		mv.addObject("racasCachorro", racas.findByEspecieOrderByNome(TipoEspecie.CACHORRO));
		mv.addObject("racasGato", racas.findByEspecieOrderByNome(TipoEspecie.GATO));
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Animal animal, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(animal);
		}
		
		animalService.salvar(animal);
		attributes.addFlashAttribute("mensagem", "Animal salvo com sucesso!");
		return new ModelAndView("redirect:/animals/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(AnimalFilter animalFilter){
		ModelAndView mv = new ModelAndView("animal/pesquisa-animal");
		mv.addObject("sexos", TipoSexoAnimal.values());
		//mv.addObject("racas", racas.findAll());
		mv.addObject("racasCachorro", racas.findByEspecieOrderByNome(TipoEspecie.CACHORRO));
		mv.addObject("racasGato", racas.findByEspecieOrderByNome(TipoEspecie.GATO));
		
		mv.addObject("animals", animals.filtrar(animalFilter));
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
		try{
			animalService.deletar(codigo);
		}
		catch (ImpossivelExcluirEntidadeException e){
			attributes.addFlashAttribute("mensagemFalhaExclusao", e.getMessage());
			return "redirect:/animals";
		}
		attributes.addFlashAttribute("mensagem", "Animal deletado com sucesso!");
		return "redirect:/animals";
	}
	
	/*
	 * Se fizer um GET em animal dizendo que precisa 
	 * receber um request, cai nesse metodo.
	 * Usa Default como -1 para caso nao passar nada retornar um array vazio.
	 * Isso aconteceria caso selecionar uma pessoa que não tem animais, não 
	 * encontraria nada.
	 */
	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Animal> pesquisaPorCodigoPessoa(
			@RequestParam(name="pessoa", defaultValue = "-1") Long codigoPessoa){
		return animals.findByPessoaCodigo(codigoPessoa);
	}
}
