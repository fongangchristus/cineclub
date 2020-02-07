(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('FormuleDialogController', FormuleDialogController);

    FormuleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Formule'];

    function FormuleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Formule) {
        var vm = this;

        vm.formule = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.formule.id !== null) {
                Formule.update(vm.formule, onSaveSuccess, onSaveError);
            } else {
                Formule.save(vm.formule, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cineclubApp:formuleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateReservation = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
