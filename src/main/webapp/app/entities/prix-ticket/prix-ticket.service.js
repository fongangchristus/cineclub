(function() {
    'use strict';
    angular
        .module('cineclubApp')
        .factory('PrixTicket', PrixTicket);

    PrixTicket.$inject = ['$resource'];

    function PrixTicket ($resource) {
        var resourceUrl =  'api/prix-tickets/:id';

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
