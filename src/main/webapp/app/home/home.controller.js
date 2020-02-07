(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$q', '$resource', '$timeout', 'User',  'Principal', 'LoginService', '$state'];

    function HomeController ($scope, $q, $resource, $timeout, User,  Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.usersCount = 0;
        vm.packageCount = 0;
        vm.portalCount = 0;
        vm.operationCount = 0;
        vm.login = LoginService.open;
        vm.register = register;
        

     

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                initDashboardData();
            });
        }

        function register () {
            $state.go('register');
        }

        function daysInThisMonth(now) {
            return new Date(now.getFullYear(), now.getMonth()+1, 0).getDate();
        }

        function initDashboardData(){

        	// Nombre de user
            User.query().$promise.then(function (response) {
                vm.usersCount = response.length
            });
        	

        	// Evolution des opérations sur le mois

        	// Annualy summary

        	//

        }
    }
})();
