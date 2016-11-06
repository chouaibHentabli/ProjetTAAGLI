(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('CauseDetailController', CauseDetailController);

    CauseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cause', 'Accident'];

    function CauseDetailController($scope, $rootScope, $stateParams, previousState, entity, Cause, Accident) {
        var vm = this;

        vm.cause = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rsdataApp:causeUpdate', function(event, result) {
            vm.cause = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
