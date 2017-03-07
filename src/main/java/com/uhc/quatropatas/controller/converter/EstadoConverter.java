package com.uhc.quatropatas.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.uhc.quatropatas.model.Estado;

public class EstadoConverter implements Converter<String, Estado>{
	
	/* 
	 * Quando vem NULL ele nem tenta converter (Quando nao seleciona nada) 
	 */
	@Override
	public Estado convert(String codigo){
		
		/*
		 * Usando if para nao conerter caso esteja vazio
		 */
		if(!StringUtils.isEmpty(codigo)){
			Estado estado = new Estado();
			estado.setCodigo(Long.valueOf(codigo));
			return estado;
		}
		
		return null;
	}
}