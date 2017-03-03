var Quatropatas = Quatropatas || {};

Quatropatas.MaskCepNumber = (function(){
	function MaskCepNumber(){
		this.inputCepNumber = $('.js-cep-number');
	}
	
	MaskCepNumber.prototype.enable = function(){
		this.inputCepNumber.mask('00000-000');
	}
	
	return MaskCepNumber;
}());

$(function(){
	
	var maskCepNumber = new Quatropatas.MaskCepNumber();
	maskCepNumber.enable();
	
});