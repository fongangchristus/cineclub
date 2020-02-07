(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('PrixTicketDeleteController',PrixTicketDeleteController);

    PrixTicketDeleteController.$inject = ['$uibModalInstance', 'entity', 'PrixTicket'];

    function PrixTicketDeleteController($uibModalInstance, entity, PrixTicket) {
        var vm = this;

        vm.prixTicket = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PrixTicket.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
