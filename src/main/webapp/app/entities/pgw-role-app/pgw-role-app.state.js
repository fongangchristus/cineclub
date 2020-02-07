(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pgw-role-app', {
            parent: 'entity',
            url: '/pgw-role-app?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pgwitgApp.pgwRoleApp.home.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/pgw-role-app/pgw-role-apps.html',
                    controller: 'PgwRoleAppController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pgwRoleApp');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pgw-role-app-detail', {
            parent: 'pgw-role-app',
            url: '/pgw-role-app/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pgwitgApp.pgwRoleApp.detail.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/pgw-role-app/pgw-role-app-detail.html',
                    controller: 'PgwRoleAppDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pgwRoleApp');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PgwRoleApp', function($stateParams, PgwRoleApp) {
                    return PgwRoleApp.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pgw-role-app',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pgw-role-app-detail.edit', {
            parent: 'pgw-role-app-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pgw-role-app/pgw-role-app-dialog.html',
                    controller: 'PgwRoleAppDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PgwRoleApp', function(PgwRoleApp) {
                            return PgwRoleApp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pgw-role-app.new', {
            parent: 'pgw-role-app',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pgw-role-app/pgw-role-app-dialog.html',
                    controller: 'PgwRoleAppDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                libelle: null,
                                description: null,
                                permissions: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pgw-role-app', null, { reload: 'pgw-role-app' });
                }, function() {
                    $state.go('pgw-role-app');
                });
            }]
        })
        .state('pgw-role-app.edit', {
            parent: 'pgw-role-app',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pgw-role-app/pgw-role-app-dialog.html',
                    controller: 'PgwRoleAppDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PgwRoleApp', function(PgwRoleApp) {
                            return PgwRoleApp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pgw-role-app', null, { reload: 'pgw-role-app' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pgw-role-app.delete', {
            parent: 'pgw-role-app',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pgw-role-app/pgw-role-app-delete-dialog.html',
                    controller: 'PgwRoleAppDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PgwRoleApp', function(PgwRoleApp) {
                            return PgwRoleApp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pgw-role-app', null, { reload: 'pgw-role-app' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
