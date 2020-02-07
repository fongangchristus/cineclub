(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('seance', {
            parent: 'entity',
            url: '/seance?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.seance.home.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/seance/seances.html',
                    controller: 'SeanceController',
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
                    $translatePartialLoader.addPart('seance');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('seance-detail', {
            parent: 'seance',
            url: '/seance/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.seance.detail.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/seance/seance-detail.html',
                    controller: 'SeanceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('seance');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Seance', function($stateParams, Seance) {
                    return Seance.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'seance',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('seance-detail.edit', {
            parent: 'seance-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seance/seance-dialog.html',
                    controller: 'SeanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Seance', function(Seance) {
                            return Seance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('seance.new', {
            parent: 'seance',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seance/seance-dialog.html',
                    controller: 'SeanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                heureDebut: null,
                                heureFin: null,
                                description: null,
                                code: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('seance', null, { reload: 'seance' });
                }, function() {
                    $state.go('seance');
                });
            }]
        })
        .state('seance.edit', {
            parent: 'seance',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seance/seance-dialog.html',
                    controller: 'SeanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Seance', function(Seance) {
                            return Seance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seance', null, { reload: 'seance' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('seance.delete', {
            parent: 'seance',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seance/seance-delete-dialog.html',
                    controller: 'SeanceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Seance', function(Seance) {
                            return Seance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seance', null, { reload: 'seance' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
