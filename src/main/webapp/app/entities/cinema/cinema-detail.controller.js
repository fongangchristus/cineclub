(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('CinemaDetailController', CinemaDetailController);

    CinemaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cinema'];

    function CinemaDetailController($scope, $rootScope, $stateParams, previousState, entity, Cinema) {
        var vm = this;

        vm.cinema = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cineclubApp:cinemaUpdate', function(event, result) {
            vm.cinema = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
