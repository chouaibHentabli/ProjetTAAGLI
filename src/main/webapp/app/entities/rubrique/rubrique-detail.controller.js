(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('RubriqueDetailController', RubriqueDetailController);

    RubriqueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Rubrique', 'Variable', 'Baac'];

    function RubriqueDetailController($scope, $rootScope, $stateParams, previousState, entity, Rubrique, Variable, Baac) {
        var vm = this;

        vm.rubrique = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rsdataApp:rubriqueUpdate', function(event, result) {
            vm.rubrique = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
