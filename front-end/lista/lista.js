(function (angular) {
    "use strict";
    function ColaboradorListCtrl($http, service) {
        var ctrl = this;
        var url = "http://localhost:8080/api/v1/colaborador";

        ctrl.colaboradores = [];

        ctrl.colaborador = {
            nome: null,
            senha: null,
            colaboradorPai: null,
        }

        ctrl.list = async function () {
            await $http.get(url).then(function (response) {
                ctrl.colaboradores = response.data;
            });
        }

        ctrl.list();

        ctrl.getColaboradorSelected = function () {
           if(service.opc){
               ctrl.colaborador = service.colaborador;
               return service.colaborador.colaboradorPai;
           }
           if(service.colaborador && service.colaborador.id === ctrl.colaborador.id){
               return ctrl.colaborador.colaboradorPai;
           }
           return service.colaborador;
        }

        ctrl.save = async function() {
            if(ctrl.getColaboradorSelected())
                ctrl.colaborador.colaboradorPai = ctrl.getColaboradorSelected().id;

            // alert(JSON.stringify(ctrl.colaborador));

            if(ctrl.colaborador.id) {
                await $http.put(url + "/" + ctrl.colaborador.id, ctrl.colaborador)
                    .then(function (response) {
                    ctrl.list();
                    ctrl.clean();
                    service.onSelect(null)
                });
            }else {
                await $http.post(url, ctrl.colaborador).then(function (response) {
                    ctrl.list();
                    ctrl.clean();
                    service.onSelect(null)
                });
            }
        };

        ctrl.clean = function(){
            service.opc=false;
            ctrl.colaborador = {
                nome: null,
                senha: null,
                colaboradorPai: null,
            }
            angular.element(document.getElementById("password_strength")).html("");
        }
    }
    angular.module("app").component("colaboradorList", {
        templateUrl: "lista/lista.html",
        controller: ColaboradorListCtrl,
    });
})(window.angular);