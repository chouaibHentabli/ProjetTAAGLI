(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('BaacDeleteController',BaacDeleteController);

    BaacDeleteController.$inject = ['$uibModalInstance', 'entity', 'Baac'];

    function BaacDeleteController($uibModalInstance, entity, Baac) {
        var vm = this;

        vm.baac = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Baac.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
