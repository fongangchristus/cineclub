(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('projection', {
            parent: 'entity',
            url: '/projection?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.projection.home.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/projection/projections.html',
                    controller: 'ProjectionController',
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
                    $translatePartialLoader.addPart('projection');
                    $translatePartialLoader.addPart('jour');
                    $translatePartialLoader.addPart('typeProjection');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('projection-detail', {
            parent: 'projection',
            url: '/projection/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.projection.detail.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/projection/projection-detail.html',
                    controller: 'ProjectionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('projection');
                    $translatePartialLoader.addPart('jour');
                    $translatePartialLoader.addPart('typeProjection');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Projection', function($stateParams, Projection) {
                    return Projection.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'projection',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('projection-detail.edit', {
            parent: 'projection-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projection/projection-dialog.html',
                    controller: 'ProjectionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Projection', function(Projection) {
                            return Projection.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('projection.new', {
            parent: 'projection',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projection/projection-dialog.html',
                    controller: 'ProjectionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                libele: null,
                                duree: null,
                                code: null,
                                jour: null,
                                typeProjection: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('projection', null, { reload: 'projection' });
                }, function() {
                    $state.go('projection');
                });
            }]
        })
        .state('projection.edit', {
            parent: 'projection',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projection/projection-dialog.html',
                    controller: 'ProjectionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Projection', function(Projection) {
                            return Projection.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('projection', null, { reload: 'projection' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('projection.delete', {
            parent: 'projection',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/projection/projection-delete-dialog.html',
                    controller: 'ProjectionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Projection', function(Projection) {
                            return Projection.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('projection', null, { reload: 'projection' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
