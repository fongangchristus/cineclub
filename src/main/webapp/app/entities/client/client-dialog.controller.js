(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('ClientDialogController', ClientDialogController);

    ClientDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Client', 'Adresse'];

    function ClientDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Client, Adresse) {
        var vm = this;

        vm.client = entity;
        vm.clear = clear;
        vm.save = save;
        vm.adresses = Adresse.query({filter: 'client-is-null'});
        $q.all([vm.client.$promise, vm.adresses.$promise]).then(function() {
            if (!vm.client.adresseId) {
                return $q.reject();
            }
            return Adresse.get({id : vm.client.adresseId}).$promise;
        }).then(function(adresse) {
            vm.adresses.push(adresse);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.client.id !== null) {
                Client.update(vm.client, onSaveSuccess, onSaveError);
            } else {
                Client.save(vm.client, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cineclubApp:clientUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
