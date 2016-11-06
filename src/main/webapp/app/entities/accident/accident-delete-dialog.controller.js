(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('AccidentDeleteController',AccidentDeleteController);

    AccidentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Accident'];

    function AccidentDeleteController($uibModalInstance, entity, Accident) {
        var vm = this;

        vm.accident = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Accident.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
