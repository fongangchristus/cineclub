(function() {
    'use strict';
    angular
        .module('cineclubApp')
        .factory('Seance', Seance);

    Seance.$inject = ['$resource'];

    function Seance ($resource) {
        var resourceUrl =  'api/seances/:id';

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
