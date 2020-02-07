(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('CategorieFilmDeleteController',CategorieFilmDeleteController);

    CategorieFilmDeleteController.$inject = ['$uibModalInstance', 'entity', 'CategorieFilm'];

    function CategorieFilmDeleteController($uibModalInstance, entity, CategorieFilm) {
        var vm = this;

        vm.categorieFilm = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CategorieFilm.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
