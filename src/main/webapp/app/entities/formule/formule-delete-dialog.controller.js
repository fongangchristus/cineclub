(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('FormuleDeleteController',FormuleDeleteController);

    FormuleDeleteController.$inject = ['$uibModalInstance', 'entity', 'Formule'];

    function FormuleDeleteController($uibModalInstance, entity, Formule) {
        var vm = this;

        vm.formule = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Formule.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
