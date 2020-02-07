(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ticket', {
            parent: 'entity',
            url: '/ticket?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.ticket.home.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/ticket/tickets.html',
                    controller: 'TicketController',
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
                    $translatePartialLoader.addPart('ticket');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ticket-detail', {
            parent: 'ticket',
            url: '/ticket/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.ticket.detail.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/ticket/ticket-detail.html',
                    controller: 'TicketDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ticket');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Ticket', function($stateParams, Ticket) {
                    return Ticket.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ticket',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ticket-detail.edit', {
            parent: 'ticket-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket/ticket-dialog.html',
                    controller: 'TicketDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ticket', function(Ticket) {
                            return Ticket.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ticket.new', {
            parent: 'ticket',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket/ticket-dialog.html',
                    controller: 'TicketDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                codePaiement: null,
                                numeroPlace: null,
                                prix: null,
                                statutTicket: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ticket', null, { reload: 'ticket' });
                }, function() {
                    $state.go('ticket');
                });
            }]
        })
        .state('ticket.edit', {
            parent: 'ticket',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket/ticket-dialog.html',
                    controller: 'TicketDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ticket', function(Ticket) {
                            return Ticket.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ticket', null, { reload: 'ticket' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ticket.delete', {
            parent: 'ticket',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket/ticket-delete-dialog.html',
                    controller: 'TicketDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ticket', function(Ticket) {
                            return Ticket.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ticket', null, { reload: 'ticket' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
