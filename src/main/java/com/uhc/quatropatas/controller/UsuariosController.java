package com.uhc.quatropatas.controller;

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

import com.uhc.quatropatas.model.Usuario;
import com.uhc.quatropatas.model.TipoEspecie;
import com.uhc.quatropatas.repository.Usuarios;

import com.uhc.quatropatas.service.UsuarioService;
import com.uhc.quatropatas.service.exception.EmailUsuarioJaCadastradoException;

@Controller
@RequestMapping("/usuarios") //Definindo o "/usuarios" antes de todo mapping
public class UsuariosController {
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario){
		ModelAndView mv = new ModelAndView("usuario/cadastro-usuario");
		mv.addObject(usuario);
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(usuario);
		}

		try{
			usuarioService.salvar(usuario);
		}
		catch (EmailUsuarioJaCadastradoException e){
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		
		attributes.addFlashAttribute("mensagem", "Usuário salva com sucesso!");
		return new ModelAndView("redirect:/usuarios/novo");
	}
	/*
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter){
		ModelAndView mv = new ModelAndView("usuario/pesquisa-usuario");
		mv.addObject("usuarios", usuarios.findByNomeContainingIgnoreCase(
				Optional.ofNullable(usuarioFilter.getNome()).orElse("%")));
		return mv;
	}
	*/
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo){
		/*
		 *  Procura uma usuário com o código que recebeu de 
		 *  parametro da URL mapeada
		 */
		Usuario usuario = usuarios.findOne(codigo);
		
		/*
		 * Já retorna uma usuário preenchido
		 */
		return novo(usuario);
	}
	
	@DeleteMapping("/{codigo}")
	public String deletar(@PathVariable Long codigo, RedirectAttributes attributes){
		usuarioService.deletar(codigo);
		attributes.addFlashAttribute("mensagem", "Usuário deletado com sucesso!");
		return "redirect:/usuarios";
	}
}
