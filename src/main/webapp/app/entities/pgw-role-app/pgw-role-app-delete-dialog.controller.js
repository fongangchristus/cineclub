(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('PgwRoleAppDeleteController',PgwRoleAppDeleteController);

    PgwRoleAppDeleteController.$inject = ['$uibModalInstance', 'entity', 'PgwRoleApp'];

    function PgwRoleAppDeleteController($uibModalInstance, entity, PgwRoleApp) {
        var vm = this;

        vm.pgwRoleApp = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PgwRoleApp.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
