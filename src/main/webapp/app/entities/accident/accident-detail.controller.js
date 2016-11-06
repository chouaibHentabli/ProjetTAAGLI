(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('AccidentDetailController', AccidentDetailController);

    AccidentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Accident', 'Baac', 'Cause'];

    function AccidentDetailController($scope, $rootScope, $stateParams, previousState, entity, Accident, Baac, Cause) {
        var vm = this;

        vm.accident = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rsdataApp:accidentUpdate', function(event, result) {
            vm.accident = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
