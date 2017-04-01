package com.uhc.quatropatas.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {
	
	private Page<T> page;
	private UriComponentsBuilder uriBuilder;

	public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest){
		this.page = page;
		/*
		 * Tendo que construir na mão, porque o Spring tem um bug que 
		 * quando o filtro tinha um espaço ele dava erro. Porque não substituia
		 * o + por %.
		 */
		String httpUrl = httpServletRequest.getRequestURL().append(
				httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "")
				.toString().replaceAll("\\+", "%20");
		this.uriBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);
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

		Order order = isPaginaOrdenada(propriedade);
		if(order != null){
			//direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
			direcao = order.isAscending() ? "desc" : "asc";
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
		Order order = isPaginaOrdenada(propriedade);

		if(order == null){
			return false;
		}

		return page.getSort().getOrderFor(propriedade) != null ? true : false;
	}
	
	/*
	 * Se a pagina já tem uma ordenação, retorna ela.
	 * Senão retorna NULL
	 */
	private Order isPaginaOrdenada(String propriedade) {
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;
		return order;
	}
}
