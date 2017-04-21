Quatropatas = Quatropatas || {};

Quatropatas.BotaoSubmit = (function() {
	
	function BotaoSubmit() {
		this.submitBtn = $('.js-submit-btn');
		this.formulario = $('.js-formulario-principal');
	}
	
	BotaoSubmit.prototype.iniciar = function() {
		this.submitBtn.on('click', onSubmit.bind(this));
	}
	
	function onSubmit(evento) {
		/*
		 * Pra ele não fazer o comportamento default,
		 * não entrar no href
		 */
		evento.preventDefault();
		
		var botaoClicado = $(evento.target);
		var acao = botaoClicado.data('acao');
		
		var acaoInput = $('<input>');
		acaoInput.attr('name', acao);
		
		/*
		 * Passando o parametro para o formulario e submetendo via JavaScript
		 */
		this.formulario.append(acaoInput);
		this.formulario.submit();
	}
	
	return BotaoSubmit;
	
}());

$(function() {
	
	var botaoSubmit = new Quatropatas.BotaoSubmit();
	botaoSubmit.iniciar();
	
});