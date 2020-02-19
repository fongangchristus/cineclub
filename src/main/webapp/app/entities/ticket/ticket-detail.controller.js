(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('TicketDetailController', TicketDetailController);

    TicketDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ticket', 'Projection'];

    function TicketDetailController($scope, $rootScope, $stateParams, previousState, entity, Ticket, Projection) {
        var vm = this;

        vm.ticket = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cineclubApp:ticketUpdate', function(event, result) {
            vm.ticket = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
