(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('PgwRoleAppDetailController', PgwRoleAppDetailController);

    PgwRoleAppDetailController.$inject = ['$scope', '$resource', '$rootScope', '$stateParams', 'previousState', 'entity', 'PgwRoleApp'];

    function PgwRoleAppDetailController($scope, $resource, $rootScope, $stateParams, previousState, entity, PgwRoleApp) {
        var vm = this;

        vm.pgwRoleApp = entity;
        vm.previousState = previousState.name;
        vm.users = [];

        var UsersOfRole = $resource('api/user-by-role/:idRole', {}, {
            'query': {method: 'GET', isArray: true}
        });

        UsersOfRole.query({idRole: vm.pgwRoleApp.id}).$promise.then(function(data) {
            vm.users = data;
            console.log(vm.users);
            /*data.forEach(function (item) {
               if(item.roles.id == vm.pgwRoleApp.id) {
                   vm.users.push(item);
               }
           });*/
        });

        console.log(vm.pgwRoleApp);

        var unsubscribe = $rootScope.$on('pgwitgApp:pgwRoleAppUpdate', function(event, result) {
            vm.pgwRoleApp = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
