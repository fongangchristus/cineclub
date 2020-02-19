(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('TicketReserveDialogController', TicketReserveDialogController);

    TicketReserveDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TicketReserve', 'Reservation'];

    function TicketReserveDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TicketReserve, Reservation) {
        var vm = this;

        vm.ticketReserve = entity;
        vm.clear = clear;
        vm.save = save;
        vm.reservations = Reservation.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ticketReserve.id !== null) {
                TicketReserve.update(vm.ticketReserve, onSaveSuccess, onSaveError);
            } else {
                TicketReserve.save(vm.ticketReserve, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cineclubApp:ticketReserveUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
