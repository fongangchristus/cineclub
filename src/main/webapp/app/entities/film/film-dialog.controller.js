(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('FilmDialogController', FilmDialogController);

    FilmDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Film', 'CategorieFilm'];

    function FilmDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Film, CategorieFilm) {
        var vm = this;

        vm.film = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.categoriefilms = CategorieFilm.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.film.id !== null) {
                Film.update(vm.film, onSaveSuccess, onSaveError);
            } else {
                Film.save(vm.film, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cineclubApp:filmUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setCouverture = function ($file, film) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        film.couverture = base64Data;
                        film.couvertureContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.dateSortie = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
