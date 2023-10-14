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

        ctrl.strong = 1;

        ctrl.CheckStrngth = function() {
            var pswd = ctrl.colaborador.senha;
            //TextBox left blank.
            if (pswd !== null && pswd.length <= 0) {
                angular.element(document.getElementById("password_strength")).html("");
                return;
            }
            //Regular Expressions.
            var regex = new Array();
            regex.push("[A-Z]"); //Uppercase Alphabet.
            regex.push("[a-z]"); //Lowercase Alphabet.
            regex.push("[0-9]"); //Digit.
            regex.push("[$$!%*#?&]"); //Special Character.
            var passed = 0;
            //Validate for each Regular Expression.
            for (var i = 0; i < regex.length; i++) {
                if (new RegExp(regex[i]).test(pswd)) {
                    passed++;
                }
            }
            //Validate for length of Password.
            if (passed > 2 && pswd.length > 5) {
                passed++;
            }
            // //Display status.
            var color = "";
            var strength = "";
            // $scope.status = false;
            var coment = "(Senha deve conter alphanumericos, e caracteres especiais)";
            switch (passed){
                case 1: strength = "Fraca "+coment; color = "red"; break;
                case 2: strength = "Média "+coment; color = "darkorange"; break;
                case 3: strength = "Boa "+coment; color = "green"; break;
                case 4: strength = "Forte "+coment; color = "darkgreen"; break;
                case 5: strength = "Muito Forte "+coment; color = "darkgreen"; break;
                default: strength = "";
            }
            ctrl.strong = passed;
            angular.element(document.getElementById("password_strength")).html(strength);
            angular.element(document.getElementById("password_strength")).css("color", color);
            // // $scope.status = status;
        }
    }
    angular.module("app").component("colaboradorList", {
        templateUrl: "lista/lista.html",
        controller: ColaboradorListCtrl,
    });
})(window.angular);