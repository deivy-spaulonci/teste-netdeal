(function(angular) {'use strict';
    angular.module('app').service('service', function($http) {
        this.colaborador = null;
        this.opc = false;
        var url = "http://localhost:8080/api/v1/colaborador";

        this.onSelect = function (colaborador, opc) {
            this.opc = opc;
            if(this.opc && colaborador.colaboradorPai)
                $http.get(url+"/"+colaborador.colaboradorPai).then(function (response) {
                    colaborador.colaboradorPai = {};
                    colaborador.colaboradorPai.id = response.data.id;
                    colaborador.colaboradorPai.nome = response.data.nome;
                });

            this.colaborador = colaborador;
        }

        this.delete = function (id){
            var isConfirmed = confirm("Confirma a exclusão desse colaborador ?");
            if(isConfirmed){
                $http.delete(url+"/"+id).then(function (response) {
                    alert('colaborador excluído!')
                    window.location.reload();
                });
            }
        };

        this.equals = function (id) {
            return this?.colaborador?.id === id
        }
    });
})(window.angular);