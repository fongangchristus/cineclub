(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('ProjectionDeleteController',ProjectionDeleteController);

    ProjectionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Projection'];

    function ProjectionDeleteController($uibModalInstance, entity, Projection) {
        var vm = this;

        vm.projection = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Projection.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
