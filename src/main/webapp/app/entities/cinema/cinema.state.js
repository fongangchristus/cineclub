(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cinema', {
            parent: 'entity',
            url: '/cinema?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.cinema.home.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/cinema/cinemas.html',
                    controller: 'CinemaController',
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
                    $translatePartialLoader.addPart('cinema');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cinema-detail', {
            parent: 'cinema',
            url: '/cinema/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.cinema.detail.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/cinema/cinema-detail.html',
                    controller: 'CinemaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cinema');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cinema', function($stateParams, Cinema) {
                    return Cinema.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cinema',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cinema-detail.edit', {
            parent: 'cinema-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cinema/cinema-dialog.html',
                    controller: 'CinemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cinema', function(Cinema) {
                            return Cinema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cinema.new', {
            parent: 'cinema',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cinema/cinema-dialog.html',
                    controller: 'CinemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                libele: null,
                                description: null,
                                proprietaire: null,
                                lienFacebook: null,
                                liensWathsapp: null,
                                lienYoutube: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cinema', null, { reload: 'cinema' });
                }, function() {
                    $state.go('cinema');
                });
            }]
        })
        .state('cinema.edit', {
            parent: 'cinema',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cinema/cinema-dialog.html',
                    controller: 'CinemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cinema', function(Cinema) {
                            return Cinema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cinema', null, { reload: 'cinema' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cinema.delete', {
            parent: 'cinema',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cinema/cinema-delete-dialog.html',
                    controller: 'CinemaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cinema', function(Cinema) {
                            return Cinema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cinema', null, { reload: 'cinema' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
