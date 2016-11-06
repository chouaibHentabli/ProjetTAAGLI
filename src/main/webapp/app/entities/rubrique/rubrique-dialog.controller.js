(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .controller('RubriqueDialogController', RubriqueDialogController);

    RubriqueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Rubrique', 'Variable', 'Baac'];

    function RubriqueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Rubrique, Variable, Baac) {
        var vm = this;

        vm.rubrique = entity;
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
            if (vm.rubrique.id !== null) {
                Rubrique.update(vm.rubrique, onSaveSuccess, onSaveError);
            } else {
                Rubrique.save(vm.rubrique, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rsdataApp:rubriqueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
