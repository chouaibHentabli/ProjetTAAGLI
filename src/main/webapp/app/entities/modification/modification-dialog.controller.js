(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('ModificationDialogController', ModificationDialogController);

    ModificationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Modification', 'Baac'];

    function ModificationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Modification, Baac) {
        var vm = this;

        vm.modification = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.baacs = Baac.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.modification.id !== null) {
                Modification.update(vm.modification, onSaveSuccess, onSaveError);
            } else {
                Modification.save(vm.modification, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rsdataApp:modificationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateMod = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
