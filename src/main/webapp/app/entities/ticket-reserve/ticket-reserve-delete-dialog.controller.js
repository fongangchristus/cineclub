(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('TicketReserveDeleteController',TicketReserveDeleteController);

    TicketReserveDeleteController.$inject = ['$uibModalInstance', 'entity', 'TicketReserve'];

    function TicketReserveDeleteController($uibModalInstance, entity, TicketReserve) {
        var vm = this;

        vm.ticketReserve = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TicketReserve.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
