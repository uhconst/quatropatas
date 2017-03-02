/*
 * #### INACABADO  ####
 */

$(function(){
	
	var modal = $('#cadastroRapidoRacaModal');
	
	var botaoSalvar = modal.find('.js-modal-cadastro-raca-salvar-btn');
	
	var form = modal.find('form');
	form.on('submit', function(event) { event.preventDefault() });
	var url = form.attr('action');
	var inputNomeRaca = $('#nomeRaca');
	
	modal.on('shown.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalClose);
	botaoSalvar.on('click', onBotaoSalvarClick);
	
	/*
	 * Foca no input ao iniciar o modal. O focus do bootstrap
	 * não estava funcionando.
	 */
	function onModalShow(){
		inputNomeRaca.focus();
	}
	
	/*
	 * Ao fechar o Modal, seta o input como vazio.
	 * Caso contrario toda vez que cancelar e abrir de novo o valor
	 * continuaria lá.
	 */
	function onModalClose(){
		inputNomeRaca.val('');
	}
	
	/*
	 * Evento para salvar ao clicar no botão.
	 */
	function onBotaoSalvarClick(){
		var nomeRaca = inputNomeRaca.val().trim();
		//console.log('Nome da raça: ' + nomeRaca);
		/*
		$.ajax({
			url: url,
			method: 'POST'
		});*/
	}
	
});