package com.uhc.quatropatas.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.uhc.quatropatas.model.Raca;

public class RacaConverter implements Converter<String, Raca>{
	
	/* 
	 * Quando vem NULL ele nem tenta converter (Quando nao seleciona nada) 
	 */
	@Override
	public Raca convert(String codigo){
		
		/*
		 * Usando if para nao conerter caso esteja vazio
		 */
		if(!StringUtils.isEmpty(codigo)){
			Raca raca = new Raca();
			raca.setCodigo(Long.valueOf(codigo));
			return raca;
		}
		
		return null;
	}
}
