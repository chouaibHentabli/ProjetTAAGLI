(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('VariableDetailController', VariableDetailController);

    VariableDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Variable', 'Valeur', 'Rubrique'];

    function VariableDetailController($scope, $rootScope, $stateParams, previousState, entity, Variable, Valeur, Rubrique) {
        var vm = this;

        vm.variable = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rsdataApp:variableUpdate', function(event, result) {
            vm.variable = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
