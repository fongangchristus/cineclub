(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ticket-reserve', {
            parent: 'entity',
            url: '/ticket-reserve?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.ticketReserve.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ticket-reserve/ticket-reserves.html',
                    controller: 'TicketReserveController',
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
                    $translatePartialLoader.addPart('ticketReserve');
                    $translatePartialLoader.addPart('typePlace');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ticket-reserve-detail', {
            parent: 'ticket-reserve',
            url: '/ticket-reserve/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.ticketReserve.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ticket-reserve/ticket-reserve-detail.html',
                    controller: 'TicketReserveDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ticketReserve');
                    $translatePartialLoader.addPart('typePlace');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TicketReserve', function($stateParams, TicketReserve) {
                    return TicketReserve.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ticket-reserve',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ticket-reserve-detail.edit', {
            parent: 'ticket-reserve-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket-reserve/ticket-reserve-dialog.html',
                    controller: 'TicketReserveDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TicketReserve', function(TicketReserve) {
                            return TicketReserve.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ticket-reserve.new', {
            parent: 'ticket-reserve',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket-reserve/ticket-reserve-dialog.html',
                    controller: 'TicketReserveDialogController',
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
                                typePlace: null,
                                codeSup: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ticket-reserve', null, { reload: 'ticket-reserve' });
                }, function() {
                    $state.go('ticket-reserve');
                });
            }]
        })
        .state('ticket-reserve.edit', {
            parent: 'ticket-reserve',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket-reserve/ticket-reserve-dialog.html',
                    controller: 'TicketReserveDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TicketReserve', function(TicketReserve) {
                            return TicketReserve.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ticket-reserve', null, { reload: 'ticket-reserve' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ticket-reserve.delete', {
            parent: 'ticket-reserve',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket-reserve/ticket-reserve-delete-dialog.html',
                    controller: 'TicketReserveDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TicketReserve', function(TicketReserve) {
                            return TicketReserve.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ticket-reserve', null, { reload: 'ticket-reserve' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
