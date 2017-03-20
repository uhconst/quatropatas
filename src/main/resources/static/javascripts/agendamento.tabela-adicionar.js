var Quatropatas = Quatropatas || {};

Quatropatas.TabelaAdicionar = (function(){
	
	function TabelaAdicionar(){
		//this.pesquisaRapidaPessoaModal = $('#pesquisaRapidaPessoa');
		this.adicionarServicoBtn = $('.js-adicionar-servico-animal-btn');
		this.selectAnimal = $('#animal');
		this.selectServico = $('#servico');
		this.tabelaAgendamentosContainer = $('.js-tabela-servicos-agendamentos-container');
	}
	
	TabelaAdicionar.prototype.iniciar = function(){
		this.adicionarServicoBtn.on('click', onAdicionarServicoClicado.bind(this));
	}
	
	function onAdicionarServicoClicado(event){
		event.preventDefault();
		
		console.log('Value do animal: ' + this.selectAnimal.val());
		console.log('Value do serviço: ' + this.selectServico.val());
		
		var resposta = $.ajax({
			url: 'agendamentoservico',
			method: 'POST',
			data: {
				'codigoServico': this.selectServico.val(),
				'codigoAnimal': this.selectAnimal.val()
			}
		});

		resposta.done(onServicoAdicionadoNoServidor.bind(this));
	}
	
	function onServicoAdicionadoNoServidor(html){
		this.tabelaAgendamentosContainer.html(html)
		$('.js-tabela-servico').on('dblclick', onDoubleClick);
		$('.js-exclusao-agendamento-btn').on('click', onExclusaoAgendamentoClick.bind(this));
	}
	
	
	function onDoubleClick(evento){
		$(this).toggleClass('solicitando-exclusao');
	}
	
	function onExclusaoAgendamentoClick(evento){
		var codigoServico = $(evento.target).data('codigo-servico');
		var codigoAnimal = $(evento.target).data('codigo-animal');
		var resposta = $.ajax({
			url: 'agendamentoservico/' + codigoServico + '/' + codigoAnimal,
			method: 'DELETE'
		});
		resposta.done(onServicoAdicionadoNoServidor.bind(this));
	}
	
	return TabelaAdicionar;
}());

$(function(){
	
	var tabelaAdicionar = new Quatropatas.TabelaAdicionar();
	tabelaAdicionar.iniciar();
	
});