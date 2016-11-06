(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('CauseDeleteController',CauseDeleteController);

    CauseDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cause'];

    function CauseDeleteController($uibModalInstance, entity, Cause) {
        var vm = this;

        vm.cause = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cause.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
