package com.uhc.quatropatas.model;

public enum StatusAgendamento {
	
	ORCAMENTO("Orçamento"), 
	AGENDADO("Agendado"), 
	CANCELADO("Cancelado");

	private String descricao;

	StatusAgendamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
