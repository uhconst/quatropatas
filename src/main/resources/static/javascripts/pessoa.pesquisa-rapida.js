var Quatropatas = Quatropatas || {};

Quatropatas.PesquisaRapidaPessoa = (function(){
	
	function PesquisaRapidaPessoa(){
		this.pesquisaRapidaPessoaModal = $('#pesquisaRapidaPessoa');
		this.nomePessoaInput = $('#nomePessoaModal');
		this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-pessoa-btn');
		this.containerTabelaPesquisa = $('#containerTabelaPesquisaRapidaPessoa');
		this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-pessoa').html();
		this.template = Handlebars.compile(this.htmlTabelaPesquisa);
		this.mensagemErro = $('.js-mensagem-erro');
		this.pessoa = $('.js-pessoa-pesquisa-rapida');
	}
	
	PesquisaRapidaPessoa.prototype.iniciar = function(){
		this.pesquisaRapidaBtn.on('click', onPesquisaRapidaClicado.bind(this));
		this.pesquisaRapidaPessoaModal.on('shown.bs.modal', onModalShow.bind(this));
	}
	
	/*
	 * Ao iniciar foca no input do nome. Por HTML não estava funcionando
	 */
	function onModalShow(){
		this.nomePessoaInput.focus();
	}
	
	function onPesquisaRapidaClicado(event){
		event.preventDefault();
		
		$.ajax({
			url: this.pesquisaRapidaPessoaModal.find('form').attr('action'),
			method: 'GET',
			contentType: 'application/json',
			data: {
				nome: this.nomePessoaInput.val()
			},
			success: onPesquisaConcluida.bind(this),
			error: onErroPesquisa.bind(this)
		});
	}
	
	function onPesquisaConcluida(resultado){
		this.mensagemErro.addClass('hidden');
		
		var html = this.template(resultado);
		this.containerTabelaPesquisa.html(html);
		
		var tabelaPessoaPesquisaRapida = new Quatropatas.TabelaPessoaPesquisaRapida(this.pesquisaRapidaPessoaModal);
		tabelaPessoaPesquisaRapida.iniciar();
	}
	
	function onErroPesquisa(resultado){
		this.mensagemErro.removeClass('hidden');
	}
	
	return PesquisaRapidaPessoa;
}());

Quatropatas.TabelaPessoaPesquisaRapida = (function(){
	function TabelaPessoaPesquisaRapida(modal){
		this.modalCliente = modal;
		this.pessoa = $('.js-pessoa-pesquisa-rapida');
	}

	TabelaPessoaPesquisaRapida.prototype.iniciar = function(){
		this.pessoa.on('click', onPessoaSelecionada.bind(this));
	}
	
	function onPessoaSelecionada(evento){
		this.modalCliente.modal('hide');
		var pessoaSelecionada = $(evento.currentTarget);
		
		$('#nomePessoa').val(pessoaSelecionada.data('nome') + ' ' + pessoaSelecionada.data('sobrenome'));
		$('#codigoPessoa').val(pessoaSelecionada.data('codigo'));
	}
	
	return TabelaPessoaPesquisaRapida;
}());

$(function(){
	
	var pesquisaRapidaPessoa = new Quatropatas.PesquisaRapidaPessoa();
	pesquisaRapidaPessoa.iniciar();
	
});