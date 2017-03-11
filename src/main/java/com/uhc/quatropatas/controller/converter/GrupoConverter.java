package com.uhc.quatropatas.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.uhc.quatropatas.model.Grupo;

public class GrupoConverter implements Converter<String, Grupo>{
	
	/* 
	 * Quando vem NULL ele nem tenta converter (Quando nao seleciona nada) 
	 */
	@Override
	public Grupo convert(String codigo){
		
		/*
		 * Usando if para nao converter caso esteja vazio
		 */
		if(!StringUtils.isEmpty(codigo)){
			Grupo grupo = new Grupo();
			grupo.setCodigo(Long.valueOf(codigo));
			return grupo;
		}
		
		return null;
	}
}
