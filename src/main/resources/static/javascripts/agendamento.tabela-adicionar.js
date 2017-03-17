var Quatropatas = Quatropatas || {};

Quatropatas.TabelaAdicionar = (function(){
	
	function TabelaAdicionar(){
		//this.pesquisaRapidaPessoaModal = $('#pesquisaRapidaPessoa');
		this.adicionarServicoBtn = $('.js-adicionar-servico-animal-btn');
		this.selectAnimal = $('#animal');
		this.selectServico = $('#servico');
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

		resposta.done(function(data){
			console.log('retorno: ', data);
		});
	}
	
	return TabelaAdicionar;
}());

$(function(){
	
	var tabelaAdicionar = new Quatropatas.TabelaAdicionar();
	tabelaAdicionar.iniciar();
	
});