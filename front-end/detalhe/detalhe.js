(function(angular) {'use strict';
    function DetalheCtrl(service) {
        var ctrl = this;

        ctrl.onSelect = function(selected, opc) {
            ctrl.isSelected() ? service.onSelect({}, opc) : service.onSelect({...selected}, opc);
        };

        ctrl.isSelected = function() {
            return service.equals(ctrl?.colaborador?.id);
        };

        ctrl.getStrongValue = function() {
            if (ctrl?.colaborador?.pontuacao >= 0 && ctrl?.colaborador?.pontuacao < 20) { return 'Muito ruim'; }
            else if (ctrl?.colaborador?.pontuacao >= 20 && ctrl?.colaborador?.pontuacao < 40) { return 'Ruim'; }
            else if (ctrl?.colaborador?.pontuacao >= 40 && ctrl?.colaborador?.pontuacao < 50) { return 'Mediana'; }
            else if (ctrl?.colaborador?.pontuacao >= 40 && ctrl?.colaborador?.pontuacao < 70) { return 'Bom'; }
            else if (ctrl?.colaborador?.pontuacao >= 70 && ctrl?.colaborador?.pontuacao < 90) { return 'Forte'; }
            return 'Muito forte';
        }

        ctrl.scorePercentage = function() {
            return ctrl?.colaborador?.pontuacao > 100 ? 100 : ctrl?.colaborador?.pontuacao;
        };

        ctrl.getBackgroundColor = function() {
            var hue = Math.round(ctrl?.scorePercentage() * 1.2);
            return 'hsl(' + hue + ',40%,50%)';
        };

        ctrl.onDelete = function(id){
            service.delete(id);
        }

    }
    angular.module('app').component('detalhe', {
        templateUrl: 'detalhe/detalhe.html',
        controller: DetalheCtrl,
        bindings: {
            colaborador: '<',
        },
    });
})(window.angular);