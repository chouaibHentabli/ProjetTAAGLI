(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('BaacDialogController', BaacDialogController);

    BaacDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Baac', 'Modification', 'Valeur', 'Accident', 'Rubrique'];

    function BaacDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Baac, Modification, Valeur, Accident, Rubrique) {
        var vm = this;

        vm.baac = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.baacs = Baac.query();
        vm.modifications = Modification.query();
        vm.valeurs = Valeur.query();
        vm.accidents = Accident.query();
        vm.rubriques = Rubrique.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.baac.id !== null) {
                Baac.update(vm.baac, onSaveSuccess, onSaveError);
            } else {
                Baac.save(vm.baac, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rsdataApp:baacUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateCreation = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
