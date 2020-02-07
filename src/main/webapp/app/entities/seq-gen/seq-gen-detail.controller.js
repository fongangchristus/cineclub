(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('SeqGenDetailController', SeqGenDetailController);

    SeqGenDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SeqGen'];

    function SeqGenDetailController($scope, $rootScope, $stateParams, previousState, entity, SeqGen) {
        var vm = this;

        vm.seqGen = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pgwitgApp:seqGenUpdate', function(event, result) {
            vm.seqGen = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
