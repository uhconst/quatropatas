var Quatropatas = Quatropatas || {};

Quatropatas.GraficoAgendamentoPorMes = (function() {
    
    function GraficoAgendamentoPorMes() {
        this.ctx = $('#graficoAgendamentosPorMes')[0].getContext('2d');
    }
    
    GraficoAgendamentoPorMes.prototype.iniciar = function() {
    	$.ajax({
			url: 'totalPorMes',
			method: 'GET', 
			success: onDadosRecebidos.bind(this)
		});
    }
    
	function onDadosRecebidos(vendaMes) {
		var meses = [];
		var valores = [];
		vendaMes.forEach(function(obj) {
			/*
			 * Unshift insere no inicio, pra ficar na ordem
			 * correta para poder mostrar
			 */
			meses.unshift(obj[0]);
			valores.unshift(obj[1]);
		});
		
		var graficoAgendamentoPorMes = new Chart(this.ctx, {
		    type: 'line',
		    data: {
		    	labels: meses,
		    	datasets: [{
		    		label: 'Agendamentos por mês',
		    		backgroundColor: "rgba(26,179,148,0.5)",
	                pointBorderColor: "rgba(26,179,148,1)",
	                pointBackgroundColor: "#fff",
	                data: valores
		    	}]
		    },
		});
	}
    
    return GraficoAgendamentoPorMes;
    
}());

$(function() {
    var GraficoAgendamentoPorMes = new Quatropatas.GraficoAgendamentoPorMes();
    GraficoAgendamentoPorMes.iniciar();
});