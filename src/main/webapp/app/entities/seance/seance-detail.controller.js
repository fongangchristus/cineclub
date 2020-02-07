(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('SeanceDetailController', SeanceDetailController);

    SeanceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Seance'];

    function SeanceDetailController($scope, $rootScope, $stateParams, previousState, entity, Seance) {
        var vm = this;

        vm.seance = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cineclubApp:seanceUpdate', function(event, result) {
            vm.seance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
