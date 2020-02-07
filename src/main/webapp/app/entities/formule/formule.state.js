(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('formule', {
            parent: 'entity',
            url: '/formule?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.formule.home.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/formule/formules.html',
                    controller: 'FormuleController',
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
                    $translatePartialLoader.addPart('formule');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('formule-detail', {
            parent: 'formule',
            url: '/formule/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.formule.detail.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/formule/formule-detail.html',
                    controller: 'FormuleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('formule');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Formule', function($stateParams, Formule) {
                    return Formule.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'formule',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('formule-detail.edit', {
            parent: 'formule-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/formule/formule-dialog.html',
                    controller: 'FormuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Formule', function(Formule) {
                            return Formule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('formule.new', {
            parent: 'formule',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/formule/formule-dialog.html',
                    controller: 'FormuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                libele: null,
                                dateReservation: null,
                                code: null,
                                prixBalcon: null,
                                prixClassique: null,
                                statutFormule: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('formule', null, { reload: 'formule' });
                }, function() {
                    $state.go('formule');
                });
            }]
        })
        .state('formule.edit', {
            parent: 'formule',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/formule/formule-dialog.html',
                    controller: 'FormuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Formule', function(Formule) {
                            return Formule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('formule', null, { reload: 'formule' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('formule.delete', {
            parent: 'formule',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/formule/formule-delete-dialog.html',
                    controller: 'FormuleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Formule', function(Formule) {
                            return Formule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('formule', null, { reload: 'formule' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
