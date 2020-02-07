(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('ActeDialogController', ActeDialogController);

    ActeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Acte'];

    function ActeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Acte) {
        var vm = this;

        vm.acte = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.acte.id !== null) {
                Acte.update(vm.acte, onSaveSuccess, onSaveError);
            } else {
                Acte.save(vm.acte, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cineclubApp:acteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
