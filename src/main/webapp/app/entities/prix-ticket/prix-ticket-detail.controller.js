(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('PrixTicketDetailController', PrixTicketDetailController);

    PrixTicketDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PrixTicket', 'Projection'];

    function PrixTicketDetailController($scope, $rootScope, $stateParams, previousState, entity, PrixTicket, Projection) {
        var vm = this;

        vm.prixTicket = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cineclubApp:prixTicketUpdate', function(event, result) {
            vm.prixTicket = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
