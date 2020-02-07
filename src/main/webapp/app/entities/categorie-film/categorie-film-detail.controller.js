(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('CategorieFilmDetailController', CategorieFilmDetailController);

    CategorieFilmDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'CategorieFilm'];

    function CategorieFilmDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, CategorieFilm) {
        var vm = this;

        vm.categorieFilm = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('cineclubApp:categorieFilmUpdate', function(event, result) {
            vm.categorieFilm = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
