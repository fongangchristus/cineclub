(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('TicketDialogController', TicketDialogController);

    TicketDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ticket', 'PrixTicket', 'Projection', 'Reservation'];

    function TicketDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Ticket, PrixTicket, Projection, Reservation) {
        var vm = this;

        vm.ticket = entity;
        vm.clear = clear;
        vm.save = save;
        vm.prixtickets = PrixTicket.query();
        vm.projections = Projection.query();
        vm.reservations = Reservation.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ticket.id !== null) {
                Ticket.update(vm.ticket, onSaveSuccess, onSaveError);
            } else {
                Ticket.save(vm.ticket, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cineclubApp:ticketUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
