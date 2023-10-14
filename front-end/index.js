(function(angular) {'use strict';
    angular.module('app', []);
})(window.angular);

// var app = angular.module('app', []);
//
// app.controller("ColaboradorCTRL", ['$scope', '$http', function($scope, $http) {
//     $scope.titulo = "Teste NetDeal Front-End";
//
//     $scope.colaboradores = [];
//
//     $scope.colaborador = {
//         nome: null,
//         senha: null,
//         colaboradorPai: null,
//     }
//
//     $scope.getColaboradores = function() {
//         $http.get('http://localhost:8080/api/v1/colaborador')
//             .then(function(response) {
//                 $scope.colaboradores = response.data;
//             })
//             .catch(function(error) {
//                 alert('Error fetching employees: ' + error.data.message);
//             });
//     };
//
//     $scope.getColaboradores();
// }]);