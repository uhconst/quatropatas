package com.uhc.quatropatas.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.uhc.quatropatas.model.Cidade;

public class CidadeConverter implements Converter<String, Cidade>{
	
	/* 
	 * Quando vem NULL ele nem tenta converter (Quando nao seleciona nada) 
	 */
	@Override
	public Cidade convert(String codigo){
		
		/*
		 * Usando if para nao conerter caso esteja vazio
		 */
		if(!StringUtils.isEmpty(codigo)){
			Cidade cidade = new Cidade();
			cidade.setCodigo(Long.valueOf(codigo));
			return cidade;
		}
		
		return null;
	}
}