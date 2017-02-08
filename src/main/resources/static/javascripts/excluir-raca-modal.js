$('#confirmaExclusaoModal').on('show.bs.modal', function (event){
	  var botao = $(event.relatedTarget); // Botao que acionou o modal
	  var nome = botao.data('nome');
	  var url = botao.data('url-deletar');
	  
	  var modal = $(this);
	  var form = modal.find('form');
	  form.attr('action', url);
	  modal.find('.modal-body span').html('Tem certeza que deseja excluir o <strong>' + nome + '</strong>?');
});