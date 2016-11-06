(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('VariableDeleteController',VariableDeleteController);

    VariableDeleteController.$inject = ['$uibModalInstance', 'entity', 'Variable'];

    function VariableDeleteController($uibModalInstance, entity, Variable) {
        var vm = this;

        vm.variable = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Variable.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
