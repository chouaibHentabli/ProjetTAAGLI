(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('ValeurDetailController', ValeurDetailController);

    ValeurDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Valeur', 'Variable', 'Baac'];

    function ValeurDetailController($scope, $rootScope, $stateParams, previousState, entity, Valeur, Variable, Baac) {
        var vm = this;

        vm.valeur = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rsdataApp:valeurUpdate', function(event, result) {
            vm.valeur = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
