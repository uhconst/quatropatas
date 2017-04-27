var Quatropatas = Quatropatas || {};

Quatropatas.TabelaAdicionar = (function(){
	
	function TabelaAdicionar(){
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
		
		bindTabelaServico.call(this);
		
		/*
		 * Verificando se é edição para carregar os animais da pessoa
		 */
		if($('#codigo').val()){
			inicializarAnimals($('#codigoPessoa').val());
		}
	}
	
	/*
	 * Para poder acessar de fora
	 */
	TabelaAdicionar.prototype.valorTotal = function(){
		return this.tabelaAgendamentosContainer.data('valor');
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
		
		var tabelaServico = bindTabelaServico.call(this);
		
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
	
	function bindTabelaServico(){
		var tabelaServico = $('.js-tabela-servico');
		tabelaServico.on('dblclick', onDoubleClick);
		$('.js-exclusao-agendamento-btn').on('click', onExclusaoAgendamentoClick.bind(this));
		
		return tabelaServico;
	}
	
	
	/*
	 * PRECISA REFATORAR, TA O MESMO CODIGO NO pessoa.pesquisa-rapida.js
	 * Aqui removi o reset só, por estar tendo problemas e por ser desnecessario
	 * neste caso.
	 */
	function inicializarAnimals(codigoPessoa) {
		this.inputAnimal = $('#animal');
		if (codigoPessoa) {
			var resposta = $.ajax({
				url: this.inputAnimal.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: { 'pessoa': codigoPessoa }
			});
			resposta.done(onBuscarAnimalsFinalizado.bind(this));
		}
	}
	
	function onBuscarAnimalsFinalizado(animals) {
		var options = [];
		options.push('<option value="">-- Selecione a animal --</option>');
		animals.forEach(function(animal) {
			options.push('<option value="' + animal.codigo + '">' + animal.nome + '</option>');
		});
		
		this.inputAnimal.html(options.join(''));
		this.inputAnimal.removeAttr('disabled');
	}
	/*
	 * FIM DO ANIMAL. REFATORAR até aqui
	 */
	
	return TabelaAdicionar;
}());