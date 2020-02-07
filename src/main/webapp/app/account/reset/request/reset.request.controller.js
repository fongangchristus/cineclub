(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('RequestResetController', RequestResetController);

    RequestResetController.$inject = ['$timeout', '$state', 'Auth', 'errorConstants'];

    function RequestResetController ($timeout, $state, Auth, errorConstants) {
        var vm = this;

        vm.error = null;
        vm.errorEmailNotExists = null;
        vm.requestReset = requestReset;
        vm.gotologin = gotologin;
        vm.resetAccount = {};
        vm.success = null;

        $timeout(function (){angular.element('#email').focus();});

        function requestReset () {

            vm.error = null;
            vm.errorEmailNotExists = null;

            Auth.resetPasswordInit(vm.resetAccount.email).then(function () {
                vm.success = 'OK';
            }).catch(function (response) {
                vm.success = null;
                if (response.status === 400 && angular.fromJson(response.data).type === errorConstants.EMAIL_NOT_FOUND_TYPE) {
                    $timeout(function () {
                        vm.errorEmailNotExists = null;
                        console.log("exceptionnel", vm.errorEmailNotExists);
                    }, 5000);
                    vm.errorEmailNotExists = 'ERROR';
                } else {
                    vm.error = 'ERROR';
                }
            });
        }

        function gotologin() {
            $state.go('login');
        }
    }
})();
