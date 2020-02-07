(function() {
    'use strict';
    angular
        .module('cineclubApp')
        .factory('CategorieFilm', CategorieFilm);

    CategorieFilm.$inject = ['$resource'];

    function CategorieFilm ($resource) {
        var resourceUrl =  'api/categorie-films/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
