(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('prix-ticket', {
            parent: 'entity',
            url: '/prix-ticket?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.prixTicket.home.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/prix-ticket/prix-tickets.html',
                    controller: 'PrixTicketController',
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
                    $translatePartialLoader.addPart('prixTicket');
                    $translatePartialLoader.addPart('typePlace');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('prix-ticket-detail', {
            parent: 'prix-ticket',
            url: '/prix-ticket/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.prixTicket.detail.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/prix-ticket/prix-ticket-detail.html',
                    controller: 'PrixTicketDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('prixTicket');
                    $translatePartialLoader.addPart('typePlace');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PrixTicket', function($stateParams, PrixTicket) {
                    return PrixTicket.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'prix-ticket',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('prix-ticket-detail.edit', {
            parent: 'prix-ticket-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prix-ticket/prix-ticket-dialog.html',
                    controller: 'PrixTicketDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PrixTicket', function(PrixTicket) {
                            return PrixTicket.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prix-ticket.new', {
            parent: 'prix-ticket',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prix-ticket/prix-ticket-dialog.html',
                    controller: 'PrixTicketDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                prix: null,
                                code: null,
                                typePlace: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('prix-ticket', null, { reload: 'prix-ticket' });
                }, function() {
                    $state.go('prix-ticket');
                });
            }]
        })
        .state('prix-ticket.edit', {
            parent: 'prix-ticket',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prix-ticket/prix-ticket-dialog.html',
                    controller: 'PrixTicketDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PrixTicket', function(PrixTicket) {
                            return PrixTicket.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prix-ticket', null, { reload: 'prix-ticket' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prix-ticket.delete', {
            parent: 'prix-ticket',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prix-ticket/prix-ticket-delete-dialog.html',
                    controller: 'PrixTicketDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PrixTicket', function(PrixTicket) {
                            return PrixTicket.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prix-ticket', null, { reload: 'prix-ticket' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
