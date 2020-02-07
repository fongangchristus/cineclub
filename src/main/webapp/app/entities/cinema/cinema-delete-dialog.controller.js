(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('CinemaDeleteController',CinemaDeleteController);

    CinemaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cinema'];

    function CinemaDeleteController($uibModalInstance, entity, Cinema) {
        var vm = this;

        vm.cinema = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cinema.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
