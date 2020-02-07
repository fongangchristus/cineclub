(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('SeanceDialogController', SeanceDialogController);

    SeanceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Seance'];

    function SeanceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Seance) {
        var vm = this;

        vm.seance = entity;
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
            if (vm.seance.id !== null) {
                Seance.update(vm.seance, onSaveSuccess, onSaveError);
            } else {
                Seance.save(vm.seance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cineclubApp:seanceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
