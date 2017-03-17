/*var Quatropatas = Quatropatas || {};

Quatropatas.AgendamentoComboAnimal = (function(){	

	function AgendamentoComboAnimal(codigoPessoa){

	}
	AgendamentoComboAnimal.prototype.iniciar = function(codigoPessoa){
		reset.call(this);
		inicializarAnimals(pessoaSelecionada.data('codigo'));
		
	}
	function inicializarAnimals(codigoPessoa) {
	    alert("Dentro da fçao separada ");
		if (codigoPessoa) {
			var resposta = $.ajax({
				url: $('#animal').data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: { 'pessoa': codigoPessoa }
			});
			resposta.done(onBuscarAnimalsFinalizado.bind(this));
		}
		else {
			reset.call(this);
		}
	}
	
	function reset() {
		$('#animal').html('<option value="">-- Selecione a animal --</option>');
		$('#animal').val('');
		$('#animal').attr('disabled', 'disabled');
	}
	
	function onBuscarAnimalsFinalizado(animals) {
		var options = [];
		animals.forEach(function(animal) {
			console.log('Animal: ' + animal.codigo)
			options.push('<option value="' + animal.codigo + '">' + animal.nome + '</option>');
		});
		
		$('#animal').html(options.join(''));
		$('#animal').removeAttr('disabled');
		/*
		var codigoCidadeSelecionada = this.inputHiddenAnimalSelecionado.val();
		if(codigoCidadeSelecionada){
			this.combo.val(codigoCidadeSelecionada);
		}*/ /*
	}
	return AgendamentoComboAnimal;
});*/