(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('SalleDialogController', SalleDialogController);

    SalleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Salle', 'Adresse', 'Ville', 'Cinema'];

    function SalleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Salle, Adresse, Ville, Cinema) {
        var vm = this;

        vm.salle = entity;
        vm.clear = clear;
        vm.save = save;
        vm.adresses = Adresse.query({filter: 'salle-is-null'});
        $q.all([vm.salle.$promise, vm.adresses.$promise]).then(function() {
            if (!vm.salle.adresseId) {
                return $q.reject();
            }
            return Adresse.get({id : vm.salle.adresseId}).$promise;
        }).then(function(adresse) {
            vm.adresses.push(adresse);
        });
        vm.villes = Ville.query();
        vm.cinemas = Cinema.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.salle.id !== null) {
                Salle.update(vm.salle, onSaveSuccess, onSaveError);
            } else {
                Salle.save(vm.salle, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cineclubApp:salleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
