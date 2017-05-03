package com.uhc.quatropatas.dto;

/*
 * Classe para os dados do grafico no dashboard
 */
public class AgendamentoMes {

	private String mes;
	private Integer total;

	public AgendamentoMes(){
	}
	
	public AgendamentoMes(String mes, Integer total){
	    this.mes = mes;
	    this.total = total;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
