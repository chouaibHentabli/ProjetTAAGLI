(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('VariableDialogController', VariableDialogController);

    VariableDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Variable', 'Valeur', 'Rubrique'];

    function VariableDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Variable, Valeur, Rubrique) {
        var vm = this;

        vm.variable = entity;
        vm.clear = clear;
        vm.save = save;
        vm.valeurs = Valeur.query();
        vm.rubriques = Rubrique.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.variable.id !== null) {
                Variable.update(vm.variable, onSaveSuccess, onSaveError);
            } else {
                Variable.save(vm.variable, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rsdataApp:variableUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
