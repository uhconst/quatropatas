Quatropatas.Agendamento = (function(){

	function Agendamento(tabelaAgendamentos){
		this.tabelaAgendamentos = tabelaAgendamentos;
		this.valorTotalBox = $('.js-valor-total-box');
		this.valorDescontoInput = $('#valorDesconto');
		this.valorTotalBoxContainer = $('.js-valor-total-box-container');
		
		this.valorTotalServicos = this.tabelaAgendamentos.valorTotal();
		this.valorDesconto = this.valorDescontoInput.data('valor');
	}
	
	Agendamento.prototype.iniciar = function(){
		this.tabelaAgendamentos.on('tabela-agendamentos-atualizada', onTabelaAgendamentosAtualizada.bind(this));
		this.valorDescontoInput.on('keyup', onValorDescontoAlterado.bind(this));

		this.tabelaAgendamentos.on('tabela-agendamentos-atualizada', onValoresAlterados.bind(this));
		this.valorDescontoInput.on('keyup', onValoresAlterados.bind(this));
		
		onValoresAlterados().call(this);
	}
	
	function onTabelaAgendamentosAtualizada(evento, valorTotalServicos){
		this.valorTotalServicos = valorTotalServicos == null ? 0 : valorTotalServicos;
	}

	function onValorDescontoAlterado(evento){
		this.valorDesconto = Quatropatas.recuperarValor($(event.target).val());
	}

	function onValoresAlterados(){
		/*
		 * Usando Numeral paa evitar problemas de conversão de Strings
		 */
		var valorTotal = numeral(this.valorTotalServicos) - numeral(this.valorDesconto);
		
		this.valorTotalBox.html(Quatropatas.formatarMoeda(valorTotal));
		this.valorTotalBoxContainer.toggleClass('negativo', valorTotal < 0);
	}
	
	return Agendamento;
}());

$(function(){
	
	var tabelaAdicionar = new Quatropatas.TabelaAdicionar();
	tabelaAdicionar.iniciar();
	
	var agendamento = new Quatropatas.Agendamento(tabelaAdicionar);
	agendamento.iniciar();
});