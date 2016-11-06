(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('ModificationDeleteController',ModificationDeleteController);

    ModificationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Modification'];

    function ModificationDeleteController($uibModalInstance, entity, Modification) {
        var vm = this;

        vm.modification = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Modification.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
