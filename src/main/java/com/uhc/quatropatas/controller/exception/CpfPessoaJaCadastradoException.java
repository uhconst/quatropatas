package com.uhc.quatropatas.controller.exception;

public class CpfPessoaJaCadastradoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CpfPessoaJaCadastradoException(String message){
		super(message);
	}
}
