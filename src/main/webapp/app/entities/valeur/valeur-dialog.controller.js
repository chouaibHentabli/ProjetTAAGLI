(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('ValeurDialogController', ValeurDialogController);

    ValeurDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Valeur', 'Variable', 'Baac'];

    function ValeurDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Valeur, Variable, Baac) {
        var vm = this;

        vm.valeur = entity;
        vm.clear = clear;
        vm.save = save;
        vm.variables = Variable.query();
        vm.baacs = Baac.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.valeur.id !== null) {
                Valeur.update(vm.valeur, onSaveSuccess, onSaveError);
            } else {
                Valeur.save(vm.valeur, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rsdataApp:valeurUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
