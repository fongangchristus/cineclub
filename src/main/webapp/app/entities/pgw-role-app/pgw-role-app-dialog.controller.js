(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('PgwRoleAppDialogController', PgwRoleAppDialogController);

    PgwRoleAppDialogController.$inject = ['$timeout', '$resource', 'Principal', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PgwRoleApp'];

    function PgwRoleAppDialogController ($timeout, $resource, Principal, $scope, $stateParams, $uibModalInstance, entity, PgwRoleApp) {
        var vm = this;

        vm.pgwRoleApp = entity;
        vm.clear = clear;
        vm.save = save;

        // console.log(Principal.identity().authorities);

        var autorites = $resource('api/authority', {}, {
            'query': { method: 'GET', isArray: true}
        });

        vm.permissions = [];
        vm.permissions_selected = [];

        if(vm.pgwRoleApp.permissions) {
            console.log(vm.pgwRoleApp.permissions);
            console.log("Les Permissions sont là !");
            vm.pgwRoleApp.permissions.forEach(function (t) {
                vm.permissions_selected.push({name: t});
            });
        } else {
            vm.pgwRoleApp.permissions = [];
        }

        autorites.query().$promise.then(function (data) {
            console.log(data);
            data.forEach(function (item) {
                var testperm = false;
                for(var i=0; i < vm.permissions_selected.length; i++) {
                    if(item.name == vm.permissions_selected[i].name) {
                        testperm = true;
                    }
                }
                if(!testperm) {
                    vm.permissions.push(item);
                }
            });


        });


        vm.demoOptions = {
            title: 'Liste des permissions',
            filterPlaceHolder: 'Commencez à saisir pour filtrer la liste',
            labelAll: 'Toutes les permissions disponibles',
            labelSelected: 'Permissions selectionnées',
            helpMessage: ' Cliquez sur un élément pour transferer',
            /* angular will use this to filter your lists */
            orderProperty: 'name',
            /* this contains the initial list of all items (i.e. the left side) */
            items: vm.permissions,
            /* this list should be initialized as empty or with any pre-selected items */
            selectedItems: vm.permissions_selected
        };

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            vm.pgwRoleApp.permissions = [];
            vm.permissions_selected.forEach(function (t) {
                vm.pgwRoleApp.permissions.push(t.name);
            });
            if (vm.pgwRoleApp.id !== null) {
                PgwRoleApp.update(vm.pgwRoleApp, onSaveSuccess, onSaveError);
            } else {
                console.log(vm.pgwRoleApp);
                PgwRoleApp.save(vm.pgwRoleApp, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pgwitgApp:pgwRoleAppUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
