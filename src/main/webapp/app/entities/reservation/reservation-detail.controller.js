(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('ReservationDetailController', ReservationDetailController);

    ReservationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Reservation', 'Ticket', 'TicketReserve', 'Client'];

    function ReservationDetailController($scope, $rootScope, $stateParams, previousState, entity, Reservation, Ticket, TicketReserve, Client) {
        var vm = this;

        vm.reservation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cineclubApp:reservationUpdate', function(event, result) {
            vm.reservation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
