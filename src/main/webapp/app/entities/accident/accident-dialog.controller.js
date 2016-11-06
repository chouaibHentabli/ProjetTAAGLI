(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('AccidentDialogController', AccidentDialogController);

    AccidentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Accident', 'Baac', 'Cause'];

    function AccidentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Accident, Baac, Cause) {
        var vm = this;

        vm.accident = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.baacs = Baac.query();
        vm.causes = Cause.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.accident.id !== null) {
                Accident.update(vm.accident, onSaveSuccess, onSaveError);
            } else {
                Accident.save(vm.accident, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rsdataApp:accidentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateAcc = false;
        vm.datePickerOpenStatus.dateCreation = false;
        vm.datePickerOpenStatus.heure = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
