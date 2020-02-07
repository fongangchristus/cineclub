(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('categorie-film', {
            parent: 'entity',
            url: '/categorie-film?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.categorieFilm.home.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/categorie-film/categorie-films.html',
                    controller: 'CategorieFilmController',
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
                    $translatePartialLoader.addPart('categorieFilm');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('categorie-film-detail', {
            parent: 'categorie-film',
            url: '/categorie-film/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cineclubApp.categorieFilm.detail.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/categorie-film/categorie-film-detail.html',
                    controller: 'CategorieFilmDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('categorieFilm');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CategorieFilm', function($stateParams, CategorieFilm) {
                    return CategorieFilm.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'categorie-film',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('categorie-film-detail.edit', {
            parent: 'categorie-film-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/categorie-film/categorie-film-dialog.html',
                    controller: 'CategorieFilmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CategorieFilm', function(CategorieFilm) {
                            return CategorieFilm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('categorie-film.new', {
            parent: 'categorie-film',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/categorie-film/categorie-film-dialog.html',
                    controller: 'CategorieFilmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                libele: null,
                                pathCouverture: null,
                                couverture: null,
                                couvertureContentType: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('categorie-film', null, { reload: 'categorie-film' });
                }, function() {
                    $state.go('categorie-film');
                });
            }]
        })
        .state('categorie-film.edit', {
            parent: 'categorie-film',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/categorie-film/categorie-film-dialog.html',
                    controller: 'CategorieFilmDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CategorieFilm', function(CategorieFilm) {
                            return CategorieFilm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('categorie-film', null, { reload: 'categorie-film' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('categorie-film.delete', {
            parent: 'categorie-film',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/categorie-film/categorie-film-delete-dialog.html',
                    controller: 'CategorieFilmDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CategorieFilm', function(CategorieFilm) {
                            return CategorieFilm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('categorie-film', null, { reload: 'categorie-film' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
