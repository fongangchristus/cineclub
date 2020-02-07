(function() {
    'use strict';
    angular
        .module('cineclubApp')
        .factory('Acte', Acte);

    Acte.$inject = ['$resource'];

    function Acte ($resource) {
        var resourceUrl =  'api/actes/:id';

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
