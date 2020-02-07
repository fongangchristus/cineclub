(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('SeanceDeleteController',SeanceDeleteController);

    SeanceDeleteController.$inject = ['$uibModalInstance', 'entity', 'Seance'];

    function SeanceDeleteController($uibModalInstance, entity, Seance) {
        var vm = this;

        vm.seance = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Seance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
