(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('FilmDetailController', FilmDetailController);

    FilmDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Film', 'CategorieFilm'];

    function FilmDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Film, CategorieFilm) {
        var vm = this;

        vm.film = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('cineclubApp:filmUpdate', function(event, result) {
            vm.film = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
