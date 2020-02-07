(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('account', {
            abstract: true,
            parent: 'app',
            views: {
            	'navbar@app': {
                    templateUrl: 'app/layouts/navbar/navbar.html',
                    controller: 'NavbarController',
                    controllerAs: 'vm'
                },
                'sidebar@app': {
                    templateUrl: 'app/layouts/sidebar/sidebar.html',
                    controller: 'SidebarController',
                    controllerAs: 'vm'
                },
                'footer@app': {
                    templateUrl: 'app/layouts/footer/footer.html',
                    controller: 'NavbarController',
                    controllerAs: 'vm'
                }               
            }
        });
    }
})();
