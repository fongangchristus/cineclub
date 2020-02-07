(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('ClientDetailController', ClientDetailController);

    ClientDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Client', 'Adresse'];

    function ClientDetailController($scope, $rootScope, $stateParams, previousState, entity, Client, Adresse) {
        var vm = this;

        vm.client = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cineclubApp:clientUpdate', function(event, result) {
            vm.client = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
