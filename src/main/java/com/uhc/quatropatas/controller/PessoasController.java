package com.uhc.quatropatas.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uhc.quatropatas.service.exception.CpfPessoaJaCadastradoException;
import com.uhc.quatropatas.model.Pessoa;
import com.uhc.quatropatas.model.TipoSexo;
import com.uhc.quatropatas.repository.Estados;
import com.uhc.quatropatas.repository.Pessoas;
import com.uhc.quatropatas.repository.filter.PessoaFilter;
import com.uhc.quatropatas.service.PessoaService;

@Controller
@RequestMapping("/pessoas") //Definindo o "/pessoas" antes de todo mapping
public class PessoasController {
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private Pessoas pessoas;
	
	/*
	@Autowired
	private Cidades cidades;*/
	
	/*
	 * Convertendo Data para formato brasileiro
	 */
    @InitBinder("pessoa")
    public void customizeBinding (WebDataBinder binder) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFormatter.setLenient(false);
        binder.registerCustomEditor(Date.class, "nascimento",
                                    new CustomDateEditor(dateFormatter, true));
    }
	
	@GetMapping("/novo")
	public ModelAndView novo(Pessoa pessoa){
		ModelAndView mv = new ModelAndView("pessoa/cadastro-pessoa");
		mv.addObject(pessoa);
		mv.addObject("sexos", TipoSexo.values());
		mv.addObject("estados", estados.findAll());
		//mv.addObject("cidades", cidades.findAll());
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			System.out.println(">>>>>NASCIMENTO NO SALVAR: " + pessoa.getNascimento()); // Apagar test
			return novo(pessoa);
		}

		try{
			System.out.println(">>>>>NASCIMENTO NO SALVAR: " + pessoa.getNascimento()); // Apagar test
			pessoaService.salvar(pessoa);
		}
		catch (CpfPessoaJaCadastradoException e){
			result.rejectValue("cpf", e.getMessage(), e.getMessage());
			return novo(pessoa);
		}
		
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
		pessoaService.deletar(codigo);
		
		attributes.addFlashAttribute("mensagem", "Pessoa deletada com sucesso!");
		return "redirect:/pessoas";
	}
	
	/*
	 * Se fizer um GET em Pessoas dizendo que precisa 
	 * receber um request, cai nesse metodo.
	 */
	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Pessoa> pesquisar(String nome){
		validarTamanhoNome(nome);
		return pessoas.findByNomeStartingWithIgnoreCase(nome);
	}
	
	/*
	 * Validando se nome não está vazio e se tem mais que 3
	 * caracteres antes de pesquisar
	 */
	private void validarTamanhoNome(String nome) {
		if (StringUtils.isEmpty(nome) || nome.length() < 3) {
			throw new IllegalArgumentException();
		}
	}
	
	/*
	 * Tratando a exceção da pesquisa rapida de cliente. Tratando localmente,
	 * só o Controler da Pessoa pode acessar essa exception
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().build();
	}
}