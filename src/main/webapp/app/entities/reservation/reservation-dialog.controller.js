(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('ReservationDialogController', ReservationDialogController);

    ReservationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Reservation', 'Ticket', 'TicketReserve', 'Client'];

    function ReservationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Reservation, Ticket, TicketReserve, Client) {
        var vm = this;

        vm.reservation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.tickets = Ticket.query({filter: 'reservation-is-null'});
        $q.all([vm.reservation.$promise, vm.tickets.$promise]).then(function() {
            if (!vm.reservation.ticketId) {
                return $q.reject();
            }
            return Ticket.get({id : vm.reservation.ticketId}).$promise;
        }).then(function(ticket) {
            vm.tickets.push(ticket);
        });
        vm.ticketreserves = TicketReserve.query();
        vm.clients = Client.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.reservation.id !== null) {
                Reservation.update(vm.reservation, onSaveSuccess, onSaveError);
            } else {
                Reservation.save(vm.reservation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cineclubApp:reservationUpdate', result);
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
