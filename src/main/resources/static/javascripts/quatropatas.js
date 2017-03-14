var Quatropatas = Quatropatas || {};

Quatropatas.MaskPhoneNumber = (function(){
	function MaskPhoneNumber(){
		this.inputPhoneNumber = $('.js-phone-number');
	}
	
	MaskPhoneNumber.prototype.enable = function(){
		var maskBehavior = function (val) {
		  return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
		};
			
		var options = {
		  onKeyPress: function(val, e, field, options) {
		      field.mask(maskBehavior.apply({}, arguments), options);
		  }
		};
		this.inputPhoneNumber.mask(maskBehavior, options);
	}
	
	return MaskPhoneNumber;
}());

Quatropatas.MaskDate = (function(){
	function MaskDate(){
		this.inputDate = $('.js-date');
	};
	
	MaskDate.prototype.enable = function(){
		this.inputDate.mask('00/00/0000');
	}
	
	return MaskDate;
}());


Quatropatas.MaskMoney = (function(){
	function MaskMoney(){
		this.inputMoney = $('.js-money');
	};
	
	MaskMoney.prototype.enable = function(){
		
		this.inputMoney.maskNumber({
			decimal: ',',
			thousands: '.'
		});
	}
	
	return MaskMoney;
}());


Quatropatas.MaskMinuto = (function(){
	function MaskMinuto(){
		this.inputMinuto = $('.js-minuto');
	};
	
	MaskMinuto.prototype.enable = function(){
		
		this.inputMinuto.maskNumber({
			integer: 2,
			thousands: '.'
		});
	}
	
	return MaskMinuto;
}());


$(function(){
	
	var maskPhoneNumber = new Quatropatas.MaskPhoneNumber();
	maskPhoneNumber.enable();
	
	var maskDate = new Quatropatas.MaskDate();
	maskDate.enable();
	
	var maskMoney = new Quatropatas.MaskMoney();
	maskMoney.enable();
	
	var maskMinuto = new Quatropatas.MaskMinuto();
	maskMinuto.enable();
});









