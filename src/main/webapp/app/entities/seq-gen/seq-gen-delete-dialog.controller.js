(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('SeqGenDeleteController',SeqGenDeleteController);

    SeqGenDeleteController.$inject = ['$uibModalInstance', 'entity', 'SeqGen'];

    function SeqGenDeleteController($uibModalInstance, entity, SeqGen) {
        var vm = this;

        vm.seqGen = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SeqGen.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
