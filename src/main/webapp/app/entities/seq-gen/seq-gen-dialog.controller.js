(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('SeqGenDialogController', SeqGenDialogController);

    SeqGenDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SeqGen'];

    function SeqGenDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SeqGen) {
        var vm = this;

        vm.seqGen = entity;
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
            if (vm.seqGen.id !== null) {
                SeqGen.update(vm.seqGen, onSaveSuccess, onSaveError);
            } else {
                SeqGen.save(vm.seqGen, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pgwitgApp:seqGenUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
