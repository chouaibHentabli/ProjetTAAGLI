(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('BaacDetailController', BaacDetailController);

    BaacDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Baac', 'Modification', 'Valeur', 'Accident', 'Rubrique'];

    function BaacDetailController($scope, $rootScope, $stateParams, previousState, entity, Baac, Modification, Valeur, Accident, Rubrique) {
        var vm = this;

        vm.baac = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rsdataApp:baacUpdate', function(event, result) {
            vm.baac = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
