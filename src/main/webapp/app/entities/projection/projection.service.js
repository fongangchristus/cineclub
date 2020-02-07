(function() {
    'use strict';
    angular
        .module('cineclubApp')
        .factory('Projection', Projection);

    Projection.$inject = ['$resource'];

    function Projection ($resource) {
        var resourceUrl =  'api/projections/:id';

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
