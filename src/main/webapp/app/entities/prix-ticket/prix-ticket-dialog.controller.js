(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('PrixTicketDialogController', PrixTicketDialogController);

    PrixTicketDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PrixTicket', 'Projection'];

    function PrixTicketDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PrixTicket, Projection) {
        var vm = this;

        vm.prixTicket = entity;
        vm.clear = clear;
        vm.save = save;
        vm.projections = Projection.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.prixTicket.id !== null) {
                PrixTicket.update(vm.prixTicket, onSaveSuccess, onSaveError);
            } else {
                PrixTicket.save(vm.prixTicket, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cineclubApp:prixTicketUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
