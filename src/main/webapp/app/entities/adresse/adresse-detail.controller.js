(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('AdresseDetailController', AdresseDetailController);

    AdresseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Adresse'];

    function AdresseDetailController($scope, $rootScope, $stateParams, previousState, entity, Adresse) {
        var vm = this;

        vm.adresse = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cineclubApp:adresseUpdate', function(event, result) {
            vm.adresse = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
