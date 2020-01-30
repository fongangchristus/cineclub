(function() {
    'use strict';

    angular
        .module('tierspayantApp')
        .controller('ActeDetailController', ActeDetailController);

    ActeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Acte'];

    function ActeDetailController($scope, $rootScope, $stateParams, previousState, entity, Acte) {
        var vm = this;

        vm.acte = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tierspayantApp:acteUpdate', function(event, result) {
            vm.acte = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
