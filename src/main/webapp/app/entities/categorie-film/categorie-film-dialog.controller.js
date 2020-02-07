(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('CategorieFilmDialogController', CategorieFilmDialogController);

    CategorieFilmDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'CategorieFilm'];

    function CategorieFilmDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, CategorieFilm) {
        var vm = this;

        vm.categorieFilm = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.categorieFilm.id !== null) {
                CategorieFilm.update(vm.categorieFilm, onSaveSuccess, onSaveError);
            } else {
                CategorieFilm.save(vm.categorieFilm, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cineclubApp:categorieFilmUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setCouverture = function ($file, categorieFilm) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        categorieFilm.couverture = base64Data;
                        categorieFilm.couvertureContentType = $file.type;
                    });
                });
            }
        };

    }
})();
