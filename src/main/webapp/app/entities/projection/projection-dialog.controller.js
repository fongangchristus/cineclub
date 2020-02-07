(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('ProjectionDialogController', ProjectionDialogController);

    ProjectionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Projection', 'PrixTicket', 'Film', 'Seance', 'Salle'];

    function ProjectionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Projection, PrixTicket, Film, Seance, Salle) {
        var vm = this;

        vm.projection = entity;
        vm.clear = clear;
        vm.save = save;
        vm.prixtickets = PrixTicket.query();
        vm.films = Film.query();
        vm.seances = Seance.query();
        vm.salles = Salle.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.projection.id !== null) {
                Projection.update(vm.projection, onSaveSuccess, onSaveError);
            } else {
                Projection.save(vm.projection, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cineclubApp:projectionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
