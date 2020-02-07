(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('SalleDetailController', SalleDetailController);

    SalleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Salle', 'Adresse', 'Ville', 'Cinema'];

    function SalleDetailController($scope, $rootScope, $stateParams, previousState, entity, Salle, Adresse, Ville, Cinema) {
        var vm = this;

        vm.salle = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cineclubApp:salleUpdate', function(event, result) {
            vm.salle = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
