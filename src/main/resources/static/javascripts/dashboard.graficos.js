var Quatropatas = Quatropatas || {};

Quatropatas.GraficoAgendamentoPorMes = (function() {
    
    function GraficoAgendamentoPorMes() {
        this.ctx = $('#graficoAgendamentosPorMes')[0].getContext('2d');
    }
    
    GraficoAgendamentoPorMes.prototype.iniciar = function() {
        var graficoAgendamentosPorMes = new Chart(this.ctx, {
            type: 'line',
            data: {
                labels: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun'],
                datasets: [{
                    label: 'Agendamentos por mês',
                    backgroundColor: "rgba(26,179,148,0.5)",
                    pointBorderColor: "rgba(26,179,148,1)",
                    pointBackgroundColor: "#fff",
                    data: [10, 5, 7, 2, 9]
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