var Quatropatas = Quatropatas || {};

Quatropatas.MaskCpfNumber = (function(){
	function MaskCpfNumber(){
		this.inputCpfNumber = $('.js-cpf-number');
	}
	
	MaskCpfNumber.prototype.enable = function(){
		this.inputCpfNumber.mask('000.000.000-00', {reverse: true});
	}
	
	return MaskCpfNumber;
}());

$(function(){
	
	var maskCpfNumber = new Quatropatas.MaskCpfNumber();
	maskCpfNumber.enable();
	
});