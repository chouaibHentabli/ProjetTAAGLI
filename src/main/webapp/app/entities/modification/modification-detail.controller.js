(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('ModificationDetailController', ModificationDetailController);

    ModificationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Modification', 'Baac'];

    function ModificationDetailController($scope, $rootScope, $stateParams, previousState, entity, Modification, Baac) {
        var vm = this;

        vm.modification = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rsdataApp:modificationUpdate', function(event, result) {
            vm.modification = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
