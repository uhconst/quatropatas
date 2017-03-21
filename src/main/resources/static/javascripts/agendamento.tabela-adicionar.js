var Quatropatas = Quatropatas || {};

Quatropatas.TabelaAdicionar = (function(){
	
	function TabelaAdicionar(){
		//this.pesquisaRapidaPessoaModal = $('#pesquisaRapidaPessoa');
		this.adicionarServicoBtn = $('.js-adicionar-servico-animal-btn');
		this.selectAnimal = $('#animal');
		this.selectServico = $('#servico');
		this.tabelaAgendamentosContainer = $('.js-tabela-servicos-agendamentos-container');
		this.uuid = $('#uuid').val();
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
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
				'codigoAnimal': this.selectAnimal.val(),
				'uuid': this.uuid
			}
		});

		resposta.done(onServicoAtualizadoNoServidor.bind(this));
	}
	
	function onServicoAtualizadoNoServidor(html){
		this.tabelaAgendamentosContainer.html(html)
		var tabelaServico = $('.js-tabela-servico')
		tabelaServico.on('dblclick', onDoubleClick);
		$('.js-exclusao-agendamento-btn').on('click', onExclusaoAgendamentoClick.bind(this));
		
		this.emitter.trigger('tabela-agendamentos-atualizada', tabelaServico.data('valor-total'));
	}
	
	
	function onDoubleClick(evento){
		$(this).toggleClass('solicitando-exclusao');
	}
	
	function onExclusaoAgendamentoClick(evento){
		var codigoServico = $(evento.target).data('codigo-servico');
		var codigoAnimal = $(evento.target).data('codigo-animal');
		var resposta = $.ajax({
			url: 'agendamentoservico/' + this.uuid + '/' + codigoServico + '/' + codigoAnimal,
			method: 'DELETE'
		});
		resposta.done(onServicoAtualizadoNoServidor.bind(this));
	}
	
	return TabelaAdicionar;
}());