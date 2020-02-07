(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('CinemaDialogController', CinemaDialogController);

    CinemaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cinema'];

    function CinemaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cinema) {
        var vm = this;

        vm.cinema = entity;
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
            if (vm.cinema.id !== null) {
                Cinema.update(vm.cinema, onSaveSuccess, onSaveError);
            } else {
                Cinema.save(vm.cinema, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cineclubApp:cinemaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
