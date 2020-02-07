(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('ProjectionDetailController', ProjectionDetailController);

    ProjectionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Projection', 'PrixTicket', 'Film', 'Seance', 'Salle'];

    function ProjectionDetailController($scope, $rootScope, $stateParams, previousState, entity, Projection, PrixTicket, Film, Seance, Salle) {
        var vm = this;

        vm.projection = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cineclubApp:projectionUpdate', function(event, result) {
            vm.projection = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
