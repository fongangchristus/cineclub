(function() {
    'use strict';

    var jhiAlert = {
        template: "",
        controller: jhiAlertController
    };

    /* '<div class="alerts" ng-cloak="" role="alert">' +
                        '<div ng-repeat="alert in $ctrl.alerts" ng-class="[alert.position, {\'toast\': alert.toast}]">' +
                            '<uib-alert ng-cloak="" type="{{alert.type}}" close="alert.close($ctrl.alerts)"><pre ng-bind-html="alert.msg"></pre></uib-alert>' +
                        '</div>' +
                  '</div>' */

    angular
        .module('cineclubApp')
        .component('jhiAlert', jhiAlert);

    jhiAlertController.$inject = ['$scope', 'toaster', 'AlertService'];

    function jhiAlertController($scope, toaster, AlertService) {
        var vm = this;

        vm.alerts = AlertService.get();
        for(var i = 0; i < vm.alerts.length; i++) {
            toaster.pop(vm.alerts[i].type + "", "Ciné-club", vm.alerts[i].msg + "", 5000, 'trustedHtml');
        }

        $scope.$on('$destroy', function () {
            vm.alerts = [];
        });
    }
})();
