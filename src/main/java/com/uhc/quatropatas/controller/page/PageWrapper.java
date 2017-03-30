package com.uhc.quatropatas.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {
	
	private Page<T> page;
	private UriComponentsBuilder uriBuilder;

	public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest){
		this.page = page;
		this.uriBuilder = ServletUriComponentsBuilder.fromRequest(httpServletRequest);
	}
	
	/*
	 * Retornando o conteúdo da página
	 */
	public List<T> getConteudo(){
		return page.getContent();
	}
	
	/*
	 * Retornando se a página está vazia
	 */
	public boolean isVazia(){
		return page.getContent().isEmpty();
	}
	
	/*
	 * Retornando o número da página atual
	 */
	public int getAtual(){
		return page.getNumber();
	}
	
	/*
	 * Retornando se é a primeira página
	 */
	public boolean isPrimeira(){
		return page.isFirst();
	}

	/*
	 * Retornando se é a última página
	 */
	public boolean isUltima(){
		return page.isLast();
	}
	
	/*
	 * Retornando o total de páginas
	 */
	public int getTotal(){
		return page.getTotalPages();
	}
	
	/*
	 * Criando URL para esse número de página.
	 * Tendo que usar o encode, senão ele perde o encode na hora de montar a URL
	 * dos filtros de números principalmente
	 */
	public String urlParaPagina(int pagina) {
		return uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
	}
	
	public String urlOrdenada(String propriedade){
		UriComponentsBuilder uriBuilderOrder = UriComponentsBuilder
				.fromUriString(uriBuilder.build(true).encode().toUriString());
		
		String valorSort = String.format("%s,%s", propriedade, inverterDirecao(propriedade));
				
		return uriBuilderOrder.replaceQueryParam("sort", valorSort).build(true).encode().toUriString();
	}
	
	/*
	 * Invertendo a ordenação de um para outro
	 */
	public String inverterDirecao(String propriedade){
		String direcao = "asc";

		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;
		if(order != null){
			direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
		}

		return direcao;
	}
	
	/*
	 * Comparando se é ascendente no momento ou não para mudar o Caret no HTML
	 */
	public boolean descendente(String propriedade){
		return inverterDirecao(propriedade).equals("asc");
	}
	
	/*
	 * Comparando se está ordenado para remover o Caret no HTML caso
	 * não esteja ordenado ainda
	 */
	public boolean ordenada(String propriedade){
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;

		if(order == null){
			return false;
		}

		return page.getSort().getOrderFor(propriedade) != null ? true : false;
	}
}
