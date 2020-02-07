(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('FormuleDetailController', FormuleDetailController);

    FormuleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Formule'];

    function FormuleDetailController($scope, $rootScope, $stateParams, previousState, entity, Formule) {
        var vm = this;

        vm.formule = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cineclubApp:formuleUpdate', function(event, result) {
            vm.formule = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
