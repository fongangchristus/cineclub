(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('seq-gen', {
            parent: 'entity',
            url: '/seq-gen?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pgwitgApp.seqGen.home.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/seq-gen/seq-gens.html',
                    controller: 'SeqGenController',
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
                    $translatePartialLoader.addPart('seqGen');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('seq-gen-detail', {
            parent: 'seq-gen',
            url: '/seq-gen/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pgwitgApp.seqGen.detail.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/seq-gen/seq-gen-detail.html',
                    controller: 'SeqGenDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('seqGen');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SeqGen', function($stateParams, SeqGen) {
                    return SeqGen.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'seq-gen',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('seq-gen-detail.edit', {
            parent: 'seq-gen-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seq-gen/seq-gen-dialog.html',
                    controller: 'SeqGenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SeqGen', function(SeqGen) {
                            return SeqGen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('seq-gen.new', {
            parent: 'seq-gen',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seq-gen/seq-gen-dialog.html',
                    controller: 'SeqGenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                nextid: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('seq-gen', null, { reload: 'seq-gen' });
                }, function() {
                    $state.go('seq-gen');
                });
            }]
        })
        .state('seq-gen.edit', {
            parent: 'seq-gen',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seq-gen/seq-gen-dialog.html',
                    controller: 'SeqGenDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SeqGen', function(SeqGen) {
                            return SeqGen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seq-gen', null, { reload: 'seq-gen' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('seq-gen.delete', {
            parent: 'seq-gen',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seq-gen/seq-gen-delete-dialog.html',
                    controller: 'SeqGenDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SeqGen', function(SeqGen) {
                            return SeqGen.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seq-gen', null, { reload: 'seq-gen' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
