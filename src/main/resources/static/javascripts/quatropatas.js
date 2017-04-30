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
		this.inputMinuto = $('.js-numero-inteiro');
	};
	
	MaskMinuto.prototype.enable = function(){
		
		this.inputMinuto.maskNumber({
			integer: 2,
			thousands: '.'
		});
	}
	
	return MaskMinuto;
}());

/*
 * Campo select personalizado usando Select2
 */
Quatropatas.FormatarSelect2 = (function(){
	function FormatarSelect2(){
		this.selectField = $('.js-select2');
	};
	
	FormatarSelect2.prototype.enable = function(){
		this.selectField.select2();
	}
	
	return FormatarSelect2;
}());

/*
 * Toda requisição Ajax vai ser adicionado o Header e Token do CSRF
 */
Quatropatas.Security = (function(){
	function Security(){
		this.token = $('input[name=_csrf]').val();
		this.header =$('input[name=_csrf_header]').val();
	};
	
	Security.prototype.enable = function(){
		$(document).ajaxSend(function(event, jqxhr, settings){
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
	
	return Security;
}());

numeral.language('pt-br');

Quatropatas.formatarMoeda = function(valor) {
	return numeral(valor).format('0,0.00');
}

Quatropatas.recuperarValor = function(valorFormatado) {
	return numeral().unformat(valorFormatado);
}

$(function(){
	
	var maskPhoneNumber = new Quatropatas.MaskPhoneNumber();
	maskPhoneNumber.enable();
	
	var maskDate = new Quatropatas.MaskDate();
	maskDate.enable();
	
	var maskMoney = new Quatropatas.MaskMoney();
	maskMoney.enable();
	
	var maskMinuto = new Quatropatas.MaskMinuto();
	maskMinuto.enable();
	
	var formatarSelect2 = new Quatropatas.FormatarSelect2();
	formatarSelect2.enable();
	
	var security = new Quatropatas.Security();
	security.enable();
});