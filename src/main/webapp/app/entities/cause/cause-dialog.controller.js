(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('CauseDialogController', CauseDialogController);

    CauseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cause', 'Accident'];

    function CauseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cause, Accident) {
        var vm = this;

        vm.cause = entity;
        vm.clear = clear;
        vm.save = save;
        vm.accidents = Accident.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cause.id !== null) {
                Cause.update(vm.cause, onSaveSuccess, onSaveError);
            } else {
                Cause.save(vm.cause, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rsdataApp:causeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
