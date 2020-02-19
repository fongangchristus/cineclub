(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('TicketReserveDetailController', TicketReserveDetailController);

    TicketReserveDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TicketReserve', 'Reservation'];

    function TicketReserveDetailController($scope, $rootScope, $stateParams, previousState, entity, TicketReserve, Reservation) {
        var vm = this;

        vm.ticketReserve = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cineclubApp:ticketReserveUpdate', function(event, result) {
            vm.ticketReserve = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
